<?php

namespace App\Controller;

use App\Entity\Panier;
use App\Entity\PanierProduit;
use App\Entity\Produit;


use App\Form\PanierProduitType;
use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

use Symfony\Component\Routing\Annotation\Route;


class PanierController extends AbstractController
{

    /**
     * @Route("/", name="index")
     */
    public function index(SessionInterface $session, ProduitRepository $produitsRepository)
    {
        $panier = $session->get("panier", []);
//        $total = $session->get("panier", 0);

        // On "fabrique" les données
        $dataPanier = [];
        $total = 0;

        foreach ($panier as $id => $quantite) {
            $product = $produitsRepository->find($id);
            $dataPanier[] = [
                "produit" => $product,
                "quantite" => $quantite
            ];
            $total += $product->getPrix() * $quantite;
        }
        $session->set("total", $total);

        return $this->render('panier/index.html.twig', compact("dataPanier", "total"));
    }


    /**
     * @Route("/add", name="add")
     */


    public function add(SessionInterface $session, Request $request, ProduitRepository $produitsRepository): Response
    {
        // On récupère le panier actuel
        $panier = $session->get("panier", []);
        $id = $request->get('id');

        if (!empty($panier[$id])) {
            $panier[$id]++;
        } else {
            $panier[$id] = 1;
        }

        $total = 0;

        foreach ($panier as $id => $quantite) {
            $product = $produitsRepository->find($id);
            $total += $product->getPrix() * $quantite;
        }
        $session->set("total", $total);

        // On sauvegarde dans la session
        $session->set("panier", $panier);

        return $this->json(["session" => $session]);
    }




    /**
     * @Route("/add_item", name="add_item")
     */


    public function add_item(SessionInterface $session, Request $request, ProduitRepository $produitsRepository): Response
    {
        // On récupère le panier actuel
        $panier = $session->get("panier", []);
        $id = $request->get('id');

        if (!empty($panier[$id])) {
            $panier[$id]++;
        } else {
            $panier[$id] = 1;
        }

        $total = 0;

        foreach ($panier as $id => $quantite) {
            $product = $produitsRepository->find($id);
            $total += $product->getPrix() * $quantite;
        }
        $session->set("total", $total);

        // On sauvegarde dans la session
        $session->set("panier", $panier);

        return $this->json(["session" => $session]);
    }

    /**
     * @Route("/remove", name="remove")
     */
    public function remove(SessionInterface $session, Request $request, ProduitRepository $produitsRepository): Response
    {
        // On récupère le panier actuel
        $id = $request->get('id');
        $panier = $session->get("panier", []);

        if (!empty($panier[$id])) {
            if ($panier[$id] > 1) {
                $panier[$id]--;
            } else {
                $panier[$id]--;
            }
        }

        $total = 0;

        foreach ($panier as $id => $quantite) {
            $product = $produitsRepository->find($id);
            $total += $product->getPrix() * $quantite;
        }
        $session->set("total", $total);

        // On sauvegarde dans la session
        $session->set("panier", $panier);

        return $this->json(["session" => $session]);
    }

    /**
     * @Route("/delete_item", name="delete_item")
     */
    public function delete(SessionInterface $session, Request $request, ProduitRepository $produitsRepository): Response
    {
        // On récupère le panier actuel
        $id = $request->get('id');
        $panier = $session->get("panier", []);

        unset($panier[$id]);

        $total = 0;

        foreach ($panier as $id => $quantite) {
            $product = $produitsRepository->find($id);
            $total += $product->getPrix() * $quantite;
        }
        $session->set("total", $total);

        // On sauvegarde dans la session
        $session->set("panier", $panier);

        return $this->json(["session" => $session]);
    }

//    /**
//     * @Route("/delete_item", name="delete_item")
//     */
//    public function delete(SessionInterface $session, Request $request): Response
//    {
//        // On récupère le panier actuel
//        $id = $request->get('id');
//        $panier = $session->get("panier", []);
//
//        if (!empty($panier[$id])) {
//            unset($panier[$id]);
//        }
//
//        // On sauvegarde dans la session
//        $session->set("panier", $panier);
//
//        return $this->redirectToRoute("panier_index");
//    }

    /**
     * @Route("/delete_all", name="delete_all")
     */
    public function deleteAll(SessionInterface $session)
    {
        $session->remove("panier");

        return $this->redirectToRoute("panier_index");
    }
}



//    /**
//     * @Route("/new", name="panier_produit_new", methods={"GET","POST"})
//     */
//    public function new(Request $request): Response
//    {
//        $panierProduit = new PanierProduit();
//        $form = $this->createForm(PanierProduitType::class, $panierProduit);
//        $form->handleRequest($request);
//
//        if ($form->isSubmitted() && $form->isValid()) {
//
//
//
//            $entityManager = $this->getDoctrine()->getManager();
//            $entityManager->persist($panierProduit);
//            $entityManager->flush();
//
//            return $this->redirectToRoute('panier_produit_index');
//        }
//
//        return $this->render('panier_produit/new.html.twig', [
//            'panier_produit' => $panierProduit,
//            'form' => $form->createView(),
//        ]);
//    }


//

//    public function add (Request $req) {
//
//        $request = $this->get('request_stack')->getCurrentRequest();
//        $session = $request->getSession();
//        $object = new \stdClass();
//        $em = $this->getDoctrine()->getManager();
//        $id = $req->get("id");
//
//
//        if(!$session->has('panier'))
//        {
//            $session->set('panier' , array());
//        }
//        $object->id = $req->get("id");
//        $object->qte = $req->get("qte");
//
//        $index = true;
//        $panier = $session->get('panier');
//        foreach($panier as $p) {
//            if ($p->id == $object->id  ) {
//                $p->qte = $p->qte + $object->qte ;
//                $session->set('panier' ,$panier);
//                $index = false;
//            }
//        }
//        if ($index) {
//            array_push($panier, $object);
//            $session->set('panier' ,$panier);
//        }
//        $result = array();
//        foreach($panier as $p) {
//            $produit = $em->getRepository('Produit')->find($p->id);
//            array_push($result,[$produit->getId(),$p->qte,$produit->getNom(),$produit->getImage(),$produit->getPrix()]);
//        }
//        return new Response(json_encode($result));
//    }
//
