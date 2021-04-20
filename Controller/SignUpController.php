<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\UserType2;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Mime\Email;


class SignUpController extends AbstractController
{
    /**
     * @Route("/sign/up", name="sign_up")
     */
    public function index(): Response
    {
        return $this->render('sign_up/index.html.twig', [
            'controller_name' => 'SignUpController',
        ]);
    }

    /**
     * @param Request $request
     * @return Response
     * @Route("/register", name="register")
     */
    public function register(Request $request, UserPasswordEncoderInterface $passwordEncoder,\Swift_Mailer $mailer)
    {
        // 1) build the form
        $user = new User();
        $form = $this->createForm(UserType2::class, $user);
        $form->add('Register',SubmitType::class);

        // 2) handle the submit (will only happen on POST)
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

            // 3) Encode the password (you could also do this via Doctrine listener)
            $password = $passwordEncoder->encodePassword($user, $user->getPassword());
            $user->setPassword($password);
            $user->setRole("membre");
            $user->setState("inactive");
            //$token=bin2hex(random_bytes(15));
            $token=md5(uniqid());
            $user->setToken($token);


            // 4) save the User!
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($user);
            $entityManager->flush();

            //envoie du mail

            $email=$user->getEmail();
            $contact=$form->getData();
            dump($contact);
            $message=(new \Swift_Message('Thanks for your inscription'))
                ->setFrom('tunishow.tn@gmail.com')
                ->setTo($email)
                ->setBody(
                    $this->renderView(
                        'sign_up/activate.html.twig',['token'=> $user->getToken()]
                    ),
                    'text/html'
                );
            $mailer->send($message);
            $this->addFlash('info','check your email to activate your account');

            //return $this->redirectToRoute('afficheUser');
        }

        return $this->render(
            'sign_up/index.html.twig',array('form' => $form->createView()));
    }
    /**
     * @param $token
     * @param UserRepository $userRepository
     * @Route ("/register/{token}" , name="activation")
     */
    public function activation($token,UserRepository $userRepository){
        //on verifie si un user a ce token
        $user=$userRepository->findOneBy(['token'=>$token]);
        //si aucun user n'existe avec ce token
        if(!$user){
            //erreur
            throw $this->createNotFoundException('cet utilisateur n\'existe pas');
        }
        //on supprime le token :state =active
        $user->setToken(null);
        $user->setState('active');
        $em=$this->getDoctrine()->getManager();
        $em->persist($user);
        $em->flush();
        //on envoie un message flash
        $this->addFlash('message','vous avez bien activé votre compte');
        return $this->redirectToRoute('afficheUser');

    }


}

