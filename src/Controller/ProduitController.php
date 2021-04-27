<?php

namespace App\Controller;


use App\Repository\ProduitRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProduitController extends AbstractController
{
    /**
     * @Route("/produit", name="produit")
     */
    public function index(ProduitRepository $produitRepository, Request $request,PaginatorInterface $paginator)
    {
        $produit = $produitRepository->findAll();


        $produit = $paginator->paginate(
            $produit, /* query NOT result */
            $request->query->getInt('page', 1)/*page number*/,
            8/*limit per page*/
        );

        return $this->render('produit/index.html.twig', [
            'produit' => $produit,
        ]);
    }








}
