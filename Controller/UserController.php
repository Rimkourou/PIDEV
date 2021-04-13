<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\UserType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;


class UserController extends AbstractController
{
    /**
     * @Route("/user", name="user")
     */
    public function index(): Response
    {
        return $this->render('user/index.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
    /**
     * @param UserRepository $repository
     * @return Response
     * @Route ("/userA" ,name="afficheUser")
     */
    public function affiche(UserRepository $repository){
        // $repo=$this->getDoctrine()->getRepository(User::class);
        $user=$repository->findAll();
        return $this->render('user/index.html.twig',
            ['user'=>$user]);
    }

    /**
     * @Route ("/Supp/{id}",name="d")
     */
    public function Delete($id ,UserRepository $repository){
        $user=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();
        return $this->redirectToRoute('afficheUser');
    }

    /**
     * @param UserRepository $repository
     * @return Response
     * @Route ("/update/{id}",name="update")
     */

    function update($id,UserRepository $repository,Request $request){
        $user=$repository->find($id);
        $form=$this->createForm(UserType::class,$user);
        //$hashedPwd=password_hash($password, PASSWORD_DEFAULT);
        $form->add('update',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('afficheUser');
        }
        return $this->render('user/update.html.twig',[
            'f'=>$form->createView()
        ]);
    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("/userA/add" ,name="add")
     */
    function Add(Request $request){
        $user=new User();
        $form=$this->createForm(UserType::class,$user);
        $form->add('Ajouter',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            return $this->redirectToRoute('afficheUser');
        }
        return $this->render('user/Add.html.twig',[
            'form'=>$form->createView()
        ]);

    }

}
