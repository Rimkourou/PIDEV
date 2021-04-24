<?php

namespace App\Controller;

use App\Entity\Spectacle;
use App\Form\SpectacleFormType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Filesystem\Exception\FileNotFoundException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SpectacleController extends AbstractController
{

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

    /**
     * @Route("/titreSpec", name="afficher_titre_spec")
     */
    public function afficher_titre_spec()
    {
        $titreSpec = $this->getDoctrine()->getRepository(Spectacle::class)->findSpecTitle();
        return $this->render('SpectacleViews/titreSpec.html.twig',
            array('titreSpecList' => $titreSpec));
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
