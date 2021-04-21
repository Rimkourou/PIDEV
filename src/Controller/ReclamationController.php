<?php

namespace App\Controller;

use App\Entity\PropertySearchReclamation;
use App\Entity\Reclamation;
use App\Form\PropertySearchReclamationType;
use App\Form\ReclamationEditAdminType;
use App\Form\ReclamationType;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/reclamation")
 */
class ReclamationController extends AbstractController
{
    /**
     * @Route("/", name="reclamation_index")
     * @param ReclamationRepository $reclamationRepository
     * @param Request $request
     * @param $state
     * @return Response
     */
    public function index(ReclamationRepository $reclamationRepository, Request $request): Response
    {
        $propertySearchReclamation = new PropertySearchReclamation();
        $form = $this->createForm(PropertySearchReclamationType::class, $propertySearchReclamation);
        $form->handleRequest($request);
        $reclamation = $reclamationRepository->findAdminReclamation(1);
        if ($form->isSubmitted() && $form->isValid()) {
            $objet = $propertySearchReclamation->getObjet();
            $state = $propertySearchReclamation->getState();
//            var_dump($objet);
//            var_dump($state);
//            die();
            if ($state != null)
                $reclamation = $reclamationRepository->findAdminByState(1, $state);
            if ($objet != null) {
                $reclamation = $reclamationRepository->findAdminByObject(1, $objet);
            }
            if ($state == null and $objet == null) {
                $reclamation = $reclamationRepository->findAdminReclamation(1);
            }
            if ($state != null and $objet != null) {
                $reclamation = $reclamationRepository->findAdminByObjectAndState(1, $objet, $state);
            }

        }
        return $this->render('reclamation/index.html.twig', [//            'reclamations' => $reclamationRepository->findAll()
            'reclamations' => $reclamation,
            'form' => $form->createView(),]);
    }

    /**
     * @Route("/new", name="reclamation_new", methods={"GET","POST"})
     */
    public
    function new(Request $request): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $reclamation->setStete('pas encore');
            $entityManager->persist($reclamation);
            $entityManager->flush();

            return $this->redirectToRoute('reclamation_index');
        }

        return $this->render('reclamation/new.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="reclamation_show", methods={"GET"})
     */
    public function show(Reclamation $reclamation): Response
    {
        return $this->render('reclamation/show.html.twig', [
            'reclamation' => $reclamation,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="reclamation_edit", methods={"GET","POST"})
     * @param Request $request
     * @param Reclamation $reclamation
     * @param \Swift_Mailer $mailer
     * @return Response
     */
    public function edit(Request $request, Reclamation $reclamation, \Swift_Mailer $mailer): Response
    {

        $form = $this->createForm(ReclamationEditAdminType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();
//            var_dump($reclamation->getStete(),$request->get('mail_description'));
//            die();
            $message = (new \Swift_Message('reply to your complaint'))
                ->setFrom('tunishow.tn@gmail.com')
                ->setTo('tunishow.tn@gmail.com')
                ->setBody('<h1>Your State changed to <span style=\"color: red;\"> ' . $reclamation->getStete() . '</span></h1>'
                    . '<h4>' . $request->get('mail_description') . '</h4>', 'text/html');
            $mailer->send($message);
            return $this->redirectToRoute('reclamation_index');
        }

        return $this->render('reclamation/edit.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="reclamation_delete", methods={"POST"})
     */
    public function delete(Request $request, Reclamation $reclamation): Response
    {
        if ($this->isCsrfTokenValid('delete' . $reclamation->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($reclamation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('reclamation_index');
    }
}
