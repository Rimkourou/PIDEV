<?php

namespace App\Controller;

use App\Entity\PropertySearchSalle;
use App\Entity\SalleDeCinema;
use App\Entity\User;
use App\Form\PropertySearchSalleDeCinemaType;
use App\Form\PropertySearchSalleType;
use App\Form\SalleDeCinemaType;
use App\Repository\SalleDeCinemaRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/salle/de/cinema/admin")
 */
class SalleDeCinemaController extends AbstractController
{
    /**
     * @Route("/", name="salle_de_cinema_index")
     * @param SalleDeCinemaRepository $salleDeCinemaRepository
     * @return Response
     */
    public function index(SalleDeCinemaRepository $salleDeCinemaRepository, Request $request): Response
    {

        $propertySearchSalle = new PropertySearchSalle();
        $form = $this->createForm(PropertySearchSalleDeCinemaType::class, $propertySearchSalle);
        $form->handleRequest($request);
        $salle = $salleDeCinemaRepository->findAdminSalle(1);

        if ($form->isSubmitted() && $form->isValid()) {
            $nom = $propertySearchSalle->getName();
            if ($nom != "")
                $salle = $salleDeCinemaRepository->findAdminSalleByName(1,$nom);

        }
        return $this->render('salle_de_cinema/index.html.twig', [
            'salle_de_cinemas' => $salle,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/new", name="salle_de_cinema_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $salleDeCinema = new SalleDeCinema();
        $form = $this->createForm(SalleDeCinemaType::class, $salleDeCinema);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $user = new User();
            $user = $this->getDoctrine()->getRepository(User::class)->find(1);
            $salleDeCinema->setUser($user);
            $entityManager->persist($salleDeCinema);
            $entityManager->flush();

            return $this->redirectToRoute('salle_de_cinema_index');
        }

        return $this->render('salle_de_cinema/new.html.twig', [
            'salle_de_cinema' => $salleDeCinema,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="salle_de_cinema_show", methods={"GET"})
     */
    public function show(SalleDeCinema $salleDeCinema): Response
    {
        return $this->render('salle_de_cinema/show.html.twig', [
            'salle_de_cinema' => $salleDeCinema,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="salle_de_cinema_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, SalleDeCinema $salleDeCinema): Response
    {
        $form = $this->createForm(SalleDeCinemaType::class, $salleDeCinema);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('salle_de_cinema_index');
        }

        return $this->render('salle_de_cinema/edit.html.twig', [
            'salle_de_cinema' => $salleDeCinema,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="salle_de_cinema_delete", methods={"POST"})
     */
    public function delete(Request $request, SalleDeCinema $salleDeCinema): Response
    {
        if ($this->isCsrfTokenValid('delete' . $salleDeCinema->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($salleDeCinema);
            $entityManager->flush();
        }

        return $this->redirectToRoute('salle_de_cinema_index');
    }
}
