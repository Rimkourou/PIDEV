<?php

namespace App\Controller;

use App\Entity\Promotion;
use App\Form\PromotionType;
use App\Repository\PromotionRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\User\UserInterface;

class PromotionMembreController extends AbstractController
{
    /**
     * @Route("/promotion/membre", name="promotion_membre")
     */
    public function index(): Response
    {
        return $this->render('promotion_membre/index.html.twig', [
            'controller_name' => 'PromotionMembreController',
        ]);
    }

    /**
     * @param PromotionRepository $repository
     * @return Response
     * @Route ("/promotionA/membre" ,name="afficheP_membre")
     */
    public function affiche2(PromotionRepository $repository)
    {
        $promotion = $repository->findAll();
        return $this->render('promotion_membre/index.html.twig',
            ['promotion' => $promotion]);
    }
}
