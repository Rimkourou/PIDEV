<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\EditPassType;
use App\Form\EditProfilType;
use App\Form\SearchUserType;
use App\Form\UserType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Core\User\UserInterface;


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
    public function affiche(UserRepository $repository,Request $request){
        $user=$repository->findAll();
        $form=$this->createForm(SearchUserType::class);
        $search=$form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            //on recherche les users correspondant aux mots-clés
            $user=$repository->search($search->get('mots')
            ->getData());
        }

        return $this->render('user/index.html.twig',[
            'user'=>$user,
            'form' =>$form->createView()
        ]);
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

    function update($id,UserRepository $repository,Request $request,UserPasswordEncoderInterface $passwordEncoder){
        $user=$repository->find($id);
        $form=$this->createForm(UserType::class,$user);
        //$hashedPwd=password_hash($password, PASSWORD_DEFAULT);
        $form->add('update',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $password = $passwordEncoder->encodePassword($user, $user->getPassword());
            $user->setPassword($password);
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
    function Add(Request $request, UserPasswordEncoderInterface $passwordEncoder){
        $user=new User();
        $form=$this->createForm(UserType::class,$user);
        $form->add('Ajouter',SubmitType::class);

        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $password = $passwordEncoder->encodePassword($user, $user->getPassword());
            $user->setPassword($password);

            $em=$this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            return $this->redirectToRoute('afficheUser');
        }
        return $this->render('user/Add.html.twig',[
            'form'=>$form->createView()
        ]);

    }
    /**
     * @Route("/profile", name="profile")
     */
    public function showP()
    {
        return $this->render('user/profile.html.twig');
    }

    /**
     * @param UserRepository $repository
     * @return Response
     * @Route ("/updateProfile",name="updateProfile")
     */

    function updateProfile (UserRepository $repository,Request $request,UserInterface $user){
        //$userId = $user->getId();
        $form=$this->createForm(EditProfilType::class,$user);
        //$form->add('update',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){

            $em=$this->getDoctrine()->getManager();
            $em->flush();

            $this->addFlash('message','profil edited successfully');
            return $this->redirectToRoute('profile');
        }
        return $this->render('user/updateProfile.html.twig',[
            'f'=>$form->createView()
        ]);
    }

    /**
     * @param UserRepository $repository
     * @param Request $request
     * @param UserInterface $user
     * @Route ("/EditPassword",name="EditPassword")
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     */
    function updatePassword (Request $request,UserInterface $user,UserPasswordEncoderInterface $passwordEncoder){

        $form=$this->createForm(EditPassType::class,$user);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $password = $passwordEncoder->encodePassword($user, $user->getPassword());
            $user->setPassword($password);

            $em=$this->getDoctrine()->getManager();
            $em->flush();

            $this->addFlash('message','Password edited successfully');
            return $this->redirectToRoute('profile');
        }
        return $this->render('user/updatePassword.html.twig',[
            'f'=>$form->createView()
        ]);
    }


}
