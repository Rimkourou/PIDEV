<?php

namespace App\Controller;

use App\Entity\User;
use App\Entity\Promotion;
use App\Form\ResetPassType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

class SecurityController extends AbstractController
{
    /**
     * @Route("/login", name="login")
     */
    public function login(AuthenticationUtils $utils):Response
    {
        $error = $utils->getLastAuthenticationError();

        $lastUsername = $utils->getLastUsername();

            return $this->render('security/index.html.twig', [
                'error' => $error,
                'last_username' => $lastUsername
            ]);

    }

    /**
     * @Route("/logout", name="logout")
     */
    public function logout()
    {

    }

    /**
     * @Route ("/password_forgot" , name="password_forgot")
     * @param Request $request
     * @param UserRepository $userRepository
     * @param \Swift_Mailer $mailer
     * @param TokenGeneratorInterface $tokenGenerator
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     */
    public function forgottenPass(Request $request,UserRepository $userRepository, \Swift_Mailer $mailer,
                                  TokenGeneratorInterface $tokenGenerator){

        $form=$this->createForm(ResetPassType::class);
        //on traite le formulaire
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            //on récupére les données
            $donnees=$form->getData();
            //on cherche si un user a cet email
            $user=$userRepository->findOneByEmail($donnees['email']);
            //si user n'existe pas
            if(!$user){
                $this->addFlash('danger', 'this email address doesn\'t exist');
                return $this->redirectToRoute('login');
            }
            //on génere un token
            $token=$tokenGenerator->generateToken();
            try {
                $user->setResettoken($token);
                    $entityManager = $this->getDoctrine()->getManager();
                    $entityManager->persist($user);
                    $entityManager->flush();
            }catch(\Exception $e){
                $this->addFlash('warning', 'erreur:'.$e->getMessage());
                return $this->redirectToRoute('login');
            }

            //on genere l'url de réinitialisation de password
            $url=$this->generateUrl('reset_password',['token'=>$token],
            UrlGeneratorInterface::ABSOLUTE_URL);

            //on envoie le message
            $message=(new \Swift_Message('Forgot your Password'))
                ->setFrom('tunishow.tn@gmail.com')
                ->setTo($user->getEmail())
                ->setBody(
                   "<p>Hello,</p><p>a password_reset Request was made for the Tunishow Site
                    please click on the following link: ".$url.'</p>',
                    'text/html'
                );
            $mailer->send($message);
            $this->addFlash('message','check your email for a reset password');
            return $this->redirectToRoute('login');
        }
        return $this->render('security/forgotten_password.html.twig',['emailForm'=>$form->createView()]);
    }

    /**
     * @Route ("/reset-pass/{token}", name="reset_password")
     */
    public function resetPassword($token,Request $request,UserPasswordEncoderInterface $passwordEncoder){
        //on cherche l'user avec le token fourni
        $user=$this->getDoctrine()->getRepository(User::class)->findOneBy(['resettoken' =>$token]);
        if(!$user){
            $this->addFlash('danger','Unkown Token');
            return $this->redirectToRoute('login');
        }
        //si le formulaire est envoyé en méthode POST
        if($request->isMethod('POST')){
            //on supprime le token
            $user->setResettoken(null);
            //on chiffre le password
            $user->setPassword($passwordEncoder->encodePassword($user,$request->request->get('password')));
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($user);
            $entityManager->flush();

            $this->addFlash('message' ,'Password Modified successfully');
            return $this->redirectToRoute('login');
        }
        else{
            return $this->render('security/reset_password.html.twig',['token'=>$token]);
        }

    }
}
