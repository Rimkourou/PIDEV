<?php


namespace App\Controller;





use App\Entity\Produit;
use App\Entity\Urlizer;
use App\Form\ProduitType;
use App\Repository\ProduitRepository;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Ob\HighchartsBundle\Highcharts\Highchart;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/produitBack")
 */
class ProduitAdminController extends AbstractController
{
    /**
     * @Route("/", name="produit_index", methods={"GET"})
     */
    public function index(ProduitRepository $produitRepository): Response
    {

        return $this->render('produit/indexBack.html.twig', [
            'produits' => $produitRepository->findAll(),
        ]);
    }

    /**
     * @Route("/stat", name="stat")
     */
    public function stat(): Response


    {
        $ob = new Highchart();
        $ob->chart->renderTo('linechart');
        $ob->title->text('statistique des produits ');
        $ob->plotOptions->pie(array(
            'allowPointSelect'  => true,
            'cursor'    => 'pointer',
            'dataLabels'    => array('enabled' => false),
            'showInLegend'  => true
        ));
        $Product=$this->getDoctrine()
            ->getRepository(Produit::class)
            ->findAll();

        $data =array();
        foreach ($Product as $values)
        {
            $a =array($values->getNom(),intval($values->getPrix()));
            //var_dump($values->getPrix());
            //var_dump($values->getNom());
            array_push($data,$a);
        }

        $ob->series(array(array('type' => 'pie','name' => 'prix=', 'data' => $data)));



        // var_dump($en);

        return $this->render('produit/stat.html.twig', array(
            'chart' => $ob
        ));

    }


    /**
     * @Route("/recherche", name="rechercheProduit")
     * @param Request $request
     * @return Response
     */
    public function searchAction(ProduitRepository $repository, Request $request)
    {
        $requestString = $request->get('searchValue');
        $produits = $repository->findMenubyName($requestString);

        return $this->render('produit/search.html.twig', [
            'produits' => $produits,
        ]);
    }

    /**
     * @Route("/new", name="produit_new", methods={"GET","POST"})
     */
    public function new(Request $request,FlashyNotifier $flashy): Response
    {
        $produit = new Produit();
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            /** @var UploadedFile $uploadedFile */
            $uploadedFile = $form['imageFile']->getData();
            $destination = $this->getParameter('kernel.project_dir') . '/public/img';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFilename = Urlizer::urlize($originalFilename) . '-' . uniqid() . '.' . $uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFilename
            );
            $produit->setImage($newFilename);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($produit);
            $entityManager->flush();



            //return $this->redirectToRoute('produit_index');
            $flashy->success('product created!', 'http://your-awesome-link.com');

        }

        return $this->render('produit/new.html.twig', [
            'produit' => $produit,
            'form' => $form->createView(),
        ]);

    }

    /**
     * @Route("/{id}", name="produit_show", methods={"GET"})
     */
    public function show(Produit $produit): Response
    {
        return $this->render('produit/show.html.twig', [
            'produit' => $produit,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="produit_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Produit $produit,FlashyNotifier $flashy): Response
    {
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            /** @var UploadedFile $uploadedFile */
            $uploadedFile = $form['imageFile']->getData();
            $destination = $this->getParameter('kernel.project_dir') . '/public/img';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFilename = Urlizer::urlize($originalFilename) . '-' . uniqid() . '.' . $uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFilename
            );
            $produit->setImage($newFilename);
            $this->getDoctrine()->getManager()->flush();
            $flashy->success('product edited!', 'http://your-awesome-link.com');

            //return $this->redirectToRoute('produit_index');
        }

        return $this->render('produit/edit.html.twig', [
            'produit' => $produit,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="produit_delete", methods={"POST"})
     */
    public function delete(Request $request, Produit $produit): Response
    {
        if ($this->isCsrfTokenValid('delete' . $produit->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($produit);
            $entityManager->flush();
        }

        return $this->redirectToRoute('produit_index');
    }





}