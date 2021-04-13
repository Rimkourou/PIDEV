<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\UserType2;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

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
     * @Route("/register", name="user_registration")
     */
    public function register(Request $request/*, UserPasswordEncoderInterface $passwordEncoder*/)
    {
        // 1) build the form
        $user = new User();
        $form = $this->createForm(UserType2::class, $user);
        $form->add('Register',SubmitType::class);

        // 2) handle the submit (will only happen on POST)
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

            // 3) Encode the password (you could also do this via Doctrine listener)
            /*$password = $passwordEncoder->encodePassword($user, $user->getPassword());
            $user->setPassword($password);
            $user->setRole("membre");
            $user->setState("active");
            */

            // 4) save the User!
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($user);
            $entityManager->flush();

            //return $this->redirectToRoute('user/index.html.twig');
        }

        return $this->render(
            'sign_up/index.html.twig',
            array('form' => $form->createView())
        );
    }
}

