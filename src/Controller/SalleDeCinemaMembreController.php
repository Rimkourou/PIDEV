<?php

namespace App\Controller;

use App\Entity\Mapposition;
use App\Entity\PropertySearchSalle;
use App\Entity\SalleDeCinema;
use App\Form\PropertySearchSalleDeCinemaType;
use App\Repository\MappositionRepository;
use App\Repository\SalleDeCinemaRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SalleDeCinemaMembreController extends AbstractController
{
    /**
     * @Route("/salle/de/cinema/membre", name="salle_de_cinema_membre_index" )
     * @param SalleDeCinemaRepository $salleDeCinemaRepository
     * @return Response
     */
    public function index(SalleDeCinemaRepository $salleDeCinemaRepository, Request $request): Response
    {
        $propertySearchSalle = new PropertySearchSalle();
        $form = $this->createForm(PropertySearchSalleDeCinemaType::class, $propertySearchSalle);
        $form->handleRequest($request);
        $salle = $salleDeCinemaRepository->findAll();

        if ($form->isSubmitted() && $form->isValid()) {
            $nom = $propertySearchSalle->getName();
            if ($nom != "")
                $salle = $salleDeCinemaRepository->findMembreSalleByName($nom);

        }
        return $this->render('salle_de_cinema/membre/index.html.twig', [
            'salle_de_cinemas' => $salle,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/salle/de/cinema/membre/{id}", name="salle_de_cinema_membre_show", methods={"GET"})
     */
    public function show(SalleDeCinema $salleDeCinema): Response
    {
        $name = $salleDeCinema->getNom();
        $mapFrame = $this->getDoctrine()->getRepository(Mapposition::class)->findBy(['name' => $name]);
        $mapp = $mapFrame[0]->getContent();
        return $this->render('salle_de_cinema/membre/show.html.twig', [
            'salle_de_cinema' => $salleDeCinema,
            'map' => $mapp
        ]);
    }
}
