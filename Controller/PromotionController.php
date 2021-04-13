<?php

namespace App\Controller;


use App\Entity\Promotion;
use App\Form\PromotionType;
use App\Repository\PromotionRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\String\Slugger\AsciiSlugger;
use Symfony\Component\String\Slugger\SluggerInterface;


class PromotionController extends AbstractController
{
    /**
     * @Route("/promotion", name="promotion")
     */
    public function index(): Response
    {
        return $this->render('promotion/index.html.twig', [
            'controller_name' => 'PromotionController',
        ]);
    }
    /**
     * @param PromotionRepository $repository
     * @return Response
     * @Route ("/promotionA" ,name="afficheP")
     */
    public function affiche2(PromotionRepository $repository){
        $promotion=$repository->findAll();
        return $this->render('promotion/index.html.twig',
            ['promotion'=>$promotion]);
    }
    /**
     * @param $id
     * @param PromotionRepository $repository
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route ("/Supp2/{id}",name="delete")
     */
    public function Delete($id ,PromotionRepository $repository){
        $promotion=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($promotion);
        $em->flush();
        return $this->redirectToRoute('afficheP');
    }
    /**
     * @param PromotionRepository $repository
     * @return Response
     * @Route ("/update2/{id}",name="update2")
     */

    function update($id,PromotionRepository $repository,Request $request){
        $promotion=$repository->find($id);
        $form=$this->createForm(PromotionType::class,$promotion);
        $form->add('update',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){

            $file = $form->get('castPhoto')->getData();

            $fileName = md5(uniqid()).'.'.$file->guessExtension();

            $file->move(
                $this->getParameter('image_directory'),$fileName
            );

            $promotion->setCastphoto($fileName);


            $em=$this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('afficheP');
        }
        return $this->render('promotion/update.html.twig',[
            'f'=>$form->createView()
        ]);
    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("/promotionA/add" ,name="add2")
     */
    function Add(Request $request){
        $promotion=new Promotion();
        $form=$this->createForm(PromotionType::class,$promotion);
        $form->add('Ajouter',SubmitType::class);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){

            $file = $form->get('castPhoto')->getData();

            $fileName = md5(uniqid()).'.'.$file->guessExtension();

            $file->move(
                $this->getParameter('image_directory'),$fileName
            );

            $promotion->setCastphoto($fileName);
   //_____________________________
            $em=$this->getDoctrine()->getManager();
            $em->persist($promotion);
            $em->flush();
            return $this->redirectToRoute('afficheP');
        }
        return $this->render('promotion/Add.html.twig',[
            'form'=>$form->createView()
        ]);

    }




}
