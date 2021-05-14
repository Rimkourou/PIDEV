<?php

namespace App\Controller;

use App\Entity\Film;
use App\Entity\Planning;
use App\Entity\Salledecinema;
use App\Entity\Spectacle;
use App\Form\PlanningFormType;
use Doctrine\ORM\EntityManager;
use phpDocumentor\Reflection\Type;
use PhpParser\Node\Expr\Array_;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Filesystem\Exception\FileNotFoundException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\DateTimeNormalizer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

class PlanningController extends AbstractController
{
    /**
     * @Route("api/planning/show", name="api_planning_show")
     */
    public function showPlanning()
    {
        $listPlanning = $this->getDoctrine()->getRepository(Planning::class)->findAll();
        $serializer = new Serializer([new DateTimeNormalizer(), new ObjectNormalizer()]);
        $formatted = $serializer->normalize($listPlanning);
        return new JsonResponse($formatted);
    }
    /**
     * @Route("api/planning/get", name="api_planning_get")
     */
    public function showPlanningBySpectacleTitle(Request $request)
    {
        $listPlanning = $this->getDoctrine()->getRepository(Planning::class)->getByTitle($request->get('titre'));
        $serializer = new Serializer([new DateTimeNormalizer(), new ObjectNormalizer()]);
        $formatted = $serializer->normalize($listPlanning);
        return new JsonResponse($formatted);
    }
 /**
     * @Route("api/planning/showOrdered", name="api_planning_showOrdered")
     */
    public function showOrderedPlanning()
    {
        $listPlanning = $this->getDoctrine()->getRepository(Planning::class)->orderByType();
        $serializer = new Serializer([new DateTimeNormalizer(), new ObjectNormalizer()]);
        $formatted = $serializer->normalize($listPlanning);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("api/planning/add", name="api_planning_add")
     */
    public function addPlanning(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $planning = new Planning();
        $planning->setTitreEvent($request->get('titre'));
        $planning->setTypeEvent($request->get('type'));
        $planning->setNomSalle($request->get('salle'));
        try {
            $planning->setDate(new \DateTime($request->get('date')));
        } catch (\Exception $e) {

        }
        $planning->setHeureDebut($request->get('heureDebut'));
        $planning->setHeureFin($request->get('heureFin'));
        $em->persist($planning);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($planning);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("api/planning/edit", name="api_planning_edit")
     */
    public function editPlanning(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $planning = $em->getRepository(Planning::class)->find($request->get('id'));
        $planning->setTitreEvent($request->get('titre'));
        $planning->setTypeEvent($request->get('type'));
        $planning->setNomSalle($request->get('salle'));
        try {
            $planning->setDate(new \DateTime($request->get('date')));
        } catch (\Exception $e) {

        }
        $planning->setHeureDebut($request->get('heureDebut'));
        $planning->setHeureFin($request->get('heureFin'));
        $em->flush();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($planning) {
            return $planning->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers, $encoders);
        $formatted = $serializer->normalize($planning);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("api/planning/delete/{id}", name="api_planning_delete")
     */
    public function deletePlanning($id, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $planning = $this->getDoctrine()->getRepository(Planning::class)
            ->find( $id); //->find($request->get('id'));
        $em->remove($planning);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($planning);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("api/salle/get", name="api_salle_get")
     */
    public function getSalle()
    {
        $listPlanning = $this->getDoctrine()->getRepository(Salledecinema::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($listPlanning);
        return new JsonResponse($formatted);
    }
    /**
     * @Route("api/film/get", name="api_film_get")
     */
    public function getfilm()
    {
        $listPlanning = $this->getDoctrine()->getRepository(Film::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($listPlanning);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/planningsUser-Calendar", name="afficher_plannings_User_Calendar")
     */
    public function afficher_plannings_User_Calendar()
    {
        $listPlannings = $this->getDoctrine()->getRepository(Planning::class)->findAll();
//        $listSpectacles = $this->getDoctrine()->getRepository(Spectacle::class)->findAll();
//        $listFilms = $this->getDoctrine()->getRepository(Film::class)->findAll();
        $specRepo = $this->getDoctrine()->getRepository(Spectacle::class);
        $filmRepo = $this->getDoctrine()->getRepository(Film::class);
        return $this->render('PlanningViews/planningUserView-Calendar.html.twig', [
            'plannings' => $listPlannings,
            'specRepo' => $specRepo,
            'filmRepo' => $filmRepo,
        ]);
    }

    /**
     * @Route("/getEventByTitle", name="getEventByTitle")
     */
    public function getEventByTitle(Request $request): Response
    {
        $titre = $request->get('titre');
        $type = $request->get('type');
        if ($type == "Film") {
            $event = $this->getDoctrine()->getRepository(Film::class)->findBy(['titre' => $titre]);
        } else if ($type == "Spectacle") {
            $event = $this->getDoctrine()->getRepository(Spectacle::class)->findBy(['titre' => $titre]);
        }
        $tab = null;
        foreach ($event as $key => $value) {
            $tab = $this->json(["id" => $value->getId(), "titre" => $value->getTitre(),
                "genre" => $value->getGenre(), "img" => $type == "Film" ? $value->getImg() : $value->getImagePath()]);
        }
        return $tab;
    }

    /**
     * @Route("/planningsUser-Table/{titre}", name="afficher_plannings_User_Table_of_certain_event")
     */
    public function afficher_plannings_User_Table_of_certain_event($titre)
    {
        $listPlannings = $this->getDoctrine()->getRepository(Planning::class)->findBy(['titreEvent' => $titre]);
        return $this->render('PlanningViews/planningUserView-Table.html.twig', [
            'plannings' => $listPlannings
        ]);
    }

    /**
     * @Route("/planningsUser-Calendar/{titre}", name="afficher_plannings_User_Calendar_of_certain_event")
     */
    public function afficher_plannings_User_Calendar_of_certain_event($titre)
    {
        $listPlannings = $this->getDoctrine()->getRepository(Planning::class)->findBy(['titreEvent' => $titre]);
        return $this->render('PlanningViews/planningUserView-Calendar.html.twig', [
            'plannings' => $listPlannings
        ]);
    }

    /**
     * @Route("/planningsUser-Table", name="afficher_plannings_User_Table")
     */
    public function afficher_plannings_User()
    {
        $listPlannings = $this->getDoctrine()->getRepository(Planning::class)->findAll();
        return $this->render('PlanningViews/planningUserView-Table.html.twig', [
            'plannings' => $listPlannings
        ]);
    }


    /**
     * @Route("/plannings", name="afficher_plannings")
     */
    public function afficher_plannings()
    {
        // t3ayatli lel repository eli fi westha methode findall eli t3ayetli lel planning

        $listPlannings = $this->getDoctrine()->getRepository(Planning::class)->findAll();
        //nous rederige vers plannigAdminView.twig et yi3adilna liste de parametre listePlannings w sameha plannigs
        return $this->render('PlanningViews/planningAdminView.html.twig',
            array('plannings' => $listPlannings));
    }

//btn ajouter : corespendance par type : spec ou film

    /**
     * @Route("/plannings/new/{type}", name="new_planning")
     * Method({"GET", "POST"})
     */
    public function creer_planning(Request $request, $type)
    {
        $pln = new Planning();
        $pln->setTypeEvent($type);
        $form = $this->createForm(PlanningFormType::class, $pln);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($pln);
            $entityManager->flush();
            $this->addFlash('notice', 'Planning Ajouté Avec Succés');
            return $this->redirectToRoute('afficher_plannings');
        }
        if ($form->isSubmitted() && $form->isValid() == false) {
            $this->addFlash('error', 'Verifier votre ajout');
        }

        $listSpectacles = $this->getDoctrine()->getRepository(Spectacle::class)->findAll();
        $listFilms = $this->getDoctrine()->getRepository(Film::class)->findAll();
        // list film et spec : rederiger vers newPlan
        return $this->render('PlanningViews/newPlanning.html.twig', [
            "form_title" => "Ajouter un planning d'un " . strtolower($type),
            "form_planning" => $form->createView(),
            "list_plannings" => $listSpectacles,
            "list_Films" => $listFilms,
        ]);
    }

    /**
     * @Route("/plannings/edit/{id}", name="modifier_plannings")
     */
    public function modifier_planning(Request $request, int $id): Response
    {
        $entityManager = $this->getDoctrine()->getManager();

        $pln = $entityManager->getRepository(Planning::class)->find($id);
        $form = $this->createForm(PlanningFormType::class, $pln);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();
            return $this->redirectToRoute('afficher_plannings');
        }

        return $this->render("PlanningViews/newPlanning.html.twig", [
            "form_title" => "Modifier un planning d'un " . strtolower($pln->getTypeEvent()),
            "form_planning" => $form->createView(),
        ]);
    }


    /**
     * @Route("/plannings/delete/{id}", name="supprimer_planning")
     */
    public function supprimer_planning(int $id): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $pln = $entityManager->getRepository(Planning::class)->find($id);
        $entityManager->remove($pln);
        $entityManager->flush();

        return $this->redirectToRoute("afficher_plannings");
    }


}
