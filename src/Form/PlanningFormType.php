<?php

namespace App\Form;

use App\Entity\Planning;
use App\Entity\Salledecinema;
use App\Entity\Spectacle;
use App\Repository\FilmRepository;
use App\Repository\SalledecinemaRepository;
use App\Repository\SpectacleRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\ResetType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TimeType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
use Symfony\Component\OptionsResolver\OptionsResolver;


class PlanningFormType extends AbstractType
{


    /**
     * @var SpectacleRepository
     */
    private $spRepo;

    /**
     * @var SalledecinemaRepository
     */
    private $salRepo;
    /**
     * @var FilmRepository
     */
    private $filmRepo;


    private $typeEvent = null;


    public function __construct(SpectacleRepository $specRepo, SalledecinemaRepository $salRepo, FilmRepository $filmRepo)
    {
        $this->spRepo = $specRepo;
        $this->salRepo = $salRepo;
        $this->filmRepo = $filmRepo;
    }

    public function getTitreEvent($type)
    {
        $list = array();

        $eventList = $type === "Spectacle" ? $this->spRepo->findAll() : $this->filmRepo->findAll();

        foreach ($eventList as $e) {
            $list[$e->getTitre()] = $e->getTitre();
        }
        return $list;
    }

    public function getNomSalle()
    {
        $list = array();
        $nomSalle = $this->salRepo->findAll();

        foreach ($nomSalle as $ns) {
            $list[$ns->getNom()] = $ns->getNom();
        }
        return $list;
    }

    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('typeEvent', ChoiceType::class, [
                'attr' => [
                    'class' => 'form-select',
                    'onchange' => 'getEventTitles()',
                    'required' => true
                ],
                'placeholder' => 'Choisir un type ...',
                'choices' =>
                    [
                        'Spectacle' => 'Spectacle',
                        'Film' => 'Film',
                    ]
            ]);
        $builder->addEventListener(
            FormEvents::POST_SET_DATA,
            function (FormEvent $event) {
                $form = $event->getForm();
                $this->typeEvent = $form->get('typeEvent')->getData();
                dump($form);

                $form->add('titreEvent', ChoiceType::class, [
                    'attr' => [
                        'class' => 'form-select',
                        'required' => true,
                        'onchange' => 'getEventImage(this.value)'
                    ],
                    'placeholder' => 'Choisir un évènnement ...',
                    'choices' => $this->getTitreEvent($this->typeEvent)
                ]);
                $form->add('nomSalle', ChoiceType::class, [
                    'attr' => [
                        'class' => 'form-select',
                        'required' => true
                    ],
                    'placeholder' => 'Choisir une salle pour la projection ...',
                    'choices' => $this->getNomSalle()
                ]);
                $form->add('date', DateType::class, [
                    'widget' => 'single_text',
                    'attr' => [
                        'type' => 'date',
                        'placeholder' => 'dd-mm-yyyy',
                        'class' => 'form-control',
                    ]
                ]);
                $form->add('heureDebut', TimeType::class, [
                    'widget' => 'single_text',
                    'attr' => [
                        'type' => 'Time',
                        'placeholder' => 'dd-mm-yyyy',
                        'class' => 'form-control',
                    ]
                ]);
                $form->add('heureFin', TimeType::class, [
                    'widget' => 'single_text',
                    'attr' => [
                        'type' => 'Time',
                        'placeholder' => 'dd-mm-yyyy',
                        'class' => 'form-control',
                    ]
                ]);
                $form->add('submit', SubmitType::class, [
                    'attr' => [
                        'class' => 'btn btn-success btn-rounded btn-fw',
//                    'disabled' => 'true'
                    ],

                ]);
                $form->add('annuler', ResetType::class, [
                    'attr' => [
                        'class' => 'btn btn-danger btn-rounded btn-fw',
                        'data-dismiss' => "modal",
                        'onclick' => "window.open('/plannings','_self')"
                    ]
                ]);

            }
        );
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Planning::class,
        ]);
    }


}

