<?php

namespace App\Controller;

use App\Entity\Spectacle;
use App\Form\SpectacleFormType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Filesystem\Exception\FileNotFoundException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\DateTimeNormalizer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;


class SpectacleController extends AbstractController
{

    /**
     * @Route("api/spectacle/show", name="api_spectacle_show")
     */
    public function showSpectacle()
    {
        $listSpectacles = $this->getDoctrine()->getRepository(Spectacle::class)->findAll();
        $serializer = new Serializer([new DateTimeNormalizer(), new ObjectNormalizer()]);
        $formatted = $serializer->normalize($listSpectacles);
        return new JsonResponse($formatted);
    }/**
     * @Route("api/spectacle/showOrdered", name="api_spectacle_showOrdered")
     */
    public function showOrderedSpectacle()
    {
        $listSpectacles = $this->getDoctrine()->getRepository(Spectacle::class)->orderByDate();
        $serializer = new Serializer([new DateTimeNormalizer(), new ObjectNormalizer()]);
        $formatted = $serializer->normalize($listSpectacles);
        return new JsonResponse($formatted);
    }
    /**
     * @Route("api/spectacle/add", name="api_spectacle_add")
     */
    public function addSpectacle(Request $request){
        $em = $this->getDoctrine()->getManager();
        $spectacle = new Spectacle();
        $spectacle->setTitre($request->get('titre'));
        try {
            $spectacle->setDate(new \DateTime($request->get('date')));
        } catch (\Exception $e) {

        }
        $spectacle->setGenre($request->get('genre'));
        $spectacle->setImagePath($request->get('img'));
        $em->persist($spectacle);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($spectacle);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("api/spectacle/edit", name="api_spectacle_edit")
     */
    public function editSpectacle(Request $request){
        $em = $this->getDoctrine()->getManager();
        $spectacle = $em->getRepository(Spectacle::class)->find($request->get('id'));
        $spectacle->setTitre($request->get('titre'));
        try {
            $spectacle->setDate(new \DateTime($request->get('date')));
        } catch (\Exception $e) {
            $spectacle->setDate(new \DateTime());
        }
        $spectacle->setGenre($request->get('genre'));
        $spectacle->setImagePath($request->get('img'));
        $em->flush();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($spectacle) {
            return $spectacle->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers,$encoders);
        $formatted = $serializer->normalize($spectacle);
        return new JsonResponse($formatted);
    }
    /**
     * @Route("api/spectacle/delete/{id}", name="api_spectacle_delete")
     */
    public function deleteSpectacle($id, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $spectacle = $this->getDoctrine()->getRepository(Spectacle::class)
         //   ->find($request->get('id'));
        ->find($id);
        $em->remove($spectacle);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($spectacle);
        return new JsonResponse($formatted);
    }


    /**
     * @Route("/shows", name="afficher_spectacle")
     */
    public function afficher_spectacle()
    {
        $listSpectacles = $this->getDoctrine()->getRepository(Spectacle::class)->findAll();
        return $this->render('SpectacleViews/spectacleAdminView.html.twig',
            array('spectacles' => $listSpectacles));
    }

    /**
     * @Route("/showsUser", name="afficher_spectacle_user")
     */
    public function afficher_spectacle_user()
    {
        $listSpectacles = $this->getDoctrine()->getRepository(Spectacle::class)->findAll();
        return $this->render('SpectacleViews/spectacleUserView.html.twig',
            array('spectacles' => $listSpectacles));
    }



    public function uploadImage($file, $filename)
    {
        try {
            $file->move(
                $this->getParameter('EventImage_directory'),
                $filename
            );
        } catch (FileNotFoundException $e) {
        }
    }

    /**
     * @Route("/shows/new", name="new_show")
     * Method({"GET", "POST"})
     */
    public function creer_spectacle(Request $request)
    {
        $spec = new Spectacle();
        $form = $this->createForm(SpectacleFormType::class, $spec);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $spec->getImagePath();
            if ($file != null){
                $fileName = str_replace(" ", "+", $spec->getTitre()) . '.' . $file->getClientOriginalExtension();
                // upload image start
                $this->uploadImage($file, $fileName );
                // upload image end
                $spec->setImagePath($fileName);
            }
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($spec);
            $entityManager->flush();
            $this->addFlash('notice', 'Spectacle Ajouté Avec Succés');
            return $this->redirectToRoute('afficher_spectacle');
        }
        if ($form->isSubmitted() && $form->isValid() == false) {
            $this->addFlash('error', 'Verifier votre ajout');
        }

        return $this->render('SpectacleViews/newSpectacle.html.twig', [
            "form_title" => "Ajouter un spectacle",
            "form_spectacle" => $form->createView(),
        ]);
    }

    /**
     * @Route("/shows/edit/{id}", name="modifier_spectacle")
     */
    public function modifier_spectacle(Request $request, int $id): Response
    {
        $entityManager = $this->getDoctrine()->getManager();

        $spec = $entityManager->getRepository(Spectacle::class)->find($id);
        $form = $this->createForm(SpectacleFormType::class, $spec);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file = $spec->getImagePath();
            $fileName = str_replace(" ", "+", $spec->getTitre()) . '.' . $file->getClientOriginalExtension();
            // upload image start
            $this->uploadImage($file, $fileName );
            // upload image end
            $spec->setImagePath($fileName);
            $entityManager->flush();
            return $this->redirectToRoute('afficher_spectacle');
        }

        return $this->render("SpectacleViews/newSpectacle.html.twig", [
            "form_title" => "Modifier un spectacle",
            "form_spectacle" => $form->createView(),
        ]);
    }


    /**
     * @Route("/shows/delete/{id}", name="supprimer_spectacle")
     */
    public function supprimer_spectacle(int $id): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $spec = $entityManager->getRepository(Spectacle::class)->find($id);
        $entityManager->remove($spec);
        $entityManager->flush();

        return $this->redirectToRoute("afficher_spectacle");
    }

}
