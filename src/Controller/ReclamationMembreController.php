<?php

namespace App\Controller;

use App\Entity\PropertySearchReclamation;
use App\Entity\Reclamation;
use App\Entity\User;
use App\Form\PropertySearchReclamationType;
use App\Form\ReclamationEditAdminType;
use App\Form\ReclamationEditMembreType;
use App\Form\ReclamationMembreType;
use App\Form\ReclamationType;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ReclamationMembreController extends AbstractController
{
    /**
     * @Route("/reclamation_membre", name="reclamation_membre_index")
     */
    public function index(ReclamationRepository $reclamationRepository, Request $request): Response
    {
        $propertySearchReclamation = new PropertySearchReclamation();
        $form = $this->createForm(PropertySearchReclamationType::class, $propertySearchReclamation);
        $form->handleRequest($request);
        $reclamation = $reclamationRepository->findMembreReclamation(2);
        if ($form->isSubmitted() && $form->isValid()) {
            $objet = $propertySearchReclamation->getObjet();
            $state = $propertySearchReclamation->getState();
//            var_dump($objet);
//            var_dump($state);
//            die();
            if ($state != null)
                $reclamation = $reclamationRepository->findMembreByState(2, $state);
            if ($objet != null) {
                $reclamation = $reclamationRepository->findMembreByObject(2, $objet);
            }
            if ($state == null and $objet == null) {
                $reclamation = $reclamationRepository->findMembreReclamation(2);
            }
            if ($state != null and $objet != null) {
                $reclamation = $reclamationRepository->findMembreByObjectAndState(2, $objet, $state);
            }

        }
        return $this->render('reclamation/membre/index.html.twig', [
            'reclamations' => $reclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/reclamation_membre/new", name="reclamation_membre_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationMembreType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $reclamation->setStete('pas encore');
            $user = new User();
            $user = $this->getDoctrine()->getRepository(User::class)->find(2);
            $reclamation->setUser($user);
            $entityManager->persist($reclamation);
            $entityManager->flush();
            $this->addFlash('notice','add successfuly');
            return $this->redirectToRoute('reclamation_membre_index');
        }
        return $this->render('reclamation/membre/new.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="reclamation_membre_show", methods={"GET"})
     */
    public function show(Reclamation $reclamation): Response
    {
        return $this->render('reclamation/membre/show.html.twig', [
            'reclamation' => $reclamation,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="reclamation_edit_membre", methods={"GET","POST"})
     * @param Request $request
     * @param Reclamation $reclamation
     * @return Response
     */
    public function edit(Request $request, Reclamation $reclamation): Response
    {
        $form = $this->createForm(ReclamationEditMembreType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('reclamation_membre_index');
        }

        return $this->render('reclamation/membre/edit.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }
}
