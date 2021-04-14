<?php

namespace App\Controller;

use App\Entity\SalleDeCinema;
use App\Repository\SalleDeCinemaRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SalleDeCinemaMembreController extends AbstractController
{
    /**
     * @Route("/salle/de/cinema/membre", name="salle_de_cinema_membre_index" , methods={"GET"})
     * @param SalleDeCinemaRepository $salleDeCinemaRepository
     * @return Response
     */
    public function index(SalleDeCinemaRepository $salleDeCinemaRepository): Response
    {
        return $this->render('salle_de_cinema/membre/index.html.twig', [
            'salle_de_cinemas' => $salleDeCinemaRepository->findAll(),
        ]);
    }

    /**
     * @Route("/salle/de/cinema/membre/{id}", name="salle_de_cinema_membre_show", methods={"GET"})
     */
    public function show(SalleDeCinema $salleDeCinema): Response
    {
        return $this->render('salle_de_cinema/membre/show.html.twig', [
            'salle_de_cinema' => $salleDeCinema,
        ]);
    }
}
