<?php

namespace App\Controller;

use App\Entity\PanierProduit;
use App\Form\PanierProduitType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/panier/produit")
 */
class PanierProduitController extends AbstractController
{
    /**
     * @Route("/", name="panier_produit_index", methods={"GET"})
     */
    public function index(): Response
    {
        $panierProduits = $this->getDoctrine()
            ->getRepository(PanierProduit::class)
            ->findAll();

        return $this->render('panier_produit/index.html.twig', [
            'panier_produits' => $panierProduits,
        ]);
    }

    /**
     * @Route("/new", name="panier_produit_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $panierProduit = new PanierProduit();
        $form = $this->createForm(PanierProduitType::class, $panierProduit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {



            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($panierProduit);
            $entityManager->flush();

            return $this->redirectToRoute('panier_produit_index');
        }

        return $this->render('panier_produit/new.html.twig', [
            'panier_produit' => $panierProduit,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{panierId}", name="panier_produit_show", methods={"GET"})
     */
    public function show(PanierProduit $panierProduit): Response
    {
        return $this->render('panier_produit/show.html.twig', [
            'panier_produit' => $panierProduit,
        ]);
    }

    /**
     * @Route("/{panierId}/edit", name="panier_produit_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, PanierProduit $panierProduit): Response
    {
        $form = $this->createForm(PanierProduitType::class, $panierProduit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('panier_produit_index');
        }

        return $this->render('panier_produit/edit.html.twig', [
            'panier_produit' => $panierProduit,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{panierId}", name="panier_produit_delete", methods={"POST"})
     */
    public function delete(Request $request, PanierProduit $panierProduit): Response
    {
        if ($this->isCsrfTokenValid('delete'.$panierProduit->getPanierId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($panierProduit);
            $entityManager->flush();
        }

        return $this->redirectToRoute('panier_produit_index');
    }
}
