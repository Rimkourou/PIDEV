<?php

namespace App\Form;

use App\Controller\SpectacleController;
use App\Entity\Spectacle;
use PhpParser\Node\Stmt\Label;
use Symfony\Component\DomCrawler\Image;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\ResetType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
use Symfony\Component\OptionsResolver\OptionsResolver;

class SpectacleFormType extends AbstractType
{

    /**
     * @var SpectacleController
     */
    private $specCont;

    public function __construct(SpectacleController $specCont)
    {
        $this->specCont = $specCont;
    }

    public function uploadImage($file)
    {
        $this->specCont->uploadImage($file);
    }

    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('titre', TextType::class, [
                'attr' => [
                    'type' => 'text',
                    'class' => 'form-control',
                    'id' => 'fSpecTitre',
                    'placeholder' => 'Titre',
                    'required' => true
                ]
            ])
            ->add('date', DateType::class, [
                'widget' => 'single_text',
                'attr' => [
                    'type' => 'date',
                    'placeholder' => 'dd-mm-yyyy',
                    'class' => 'form-control',
                    'id' => 'fSpecDate'
                ]
            ])
            ->add('genre', ChoiceType::class, [
                'attr' => [
                    'class' => 'form-select',
                    'id' => 'fSpecGenre',
                    'required' => true
                ],
                'choices' => [
                    'Choisir un genre ...' => '',
                    'Musique' => 'Musique',
                    'Dance' => 'Dance',
                    'Pièce Théâtral' => 'Pièce Théâtral'
                ]
            ])
            ->add('imagePath', FileType::class, [
                'attr' => [
                    'type' => 'file',
                    'class' => 'custom-file-input',
                    'required' => false,
//                    'onchange' => 'uploadImageToDataFolder(this.id)'
                ],
//                'mapped' => false,
                'data_class' => null
            ])
            ->add('submit', SubmitType::class, [
                'attr' => [
                    'class' => 'btn btn-success btn-rounded btn-fw',
                    'id' => 'fSpecBtnSubmit'
                ]

            ])
            ->add('annuler', ResetType::class, [
                'attr' => [
                    'class' => 'btn btn-danger btn-rounded btn-fw',
                    'id' => 'fSpecBtnAnnuler',
                    'data-dismiss' => "modal",
                    'onclick' => "window.open('/shows','_self')"
                ]
            ]);

        $builder->get("imagePath")->addEventListener(
            FormEvents::PRE_SUBMIT,
            function (FormEvent $event) {
                $form = $event->getForm();
                $data = $form->getData();
                dump($form);
                dump($data);
            });
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Spectacle::class,
        ]);
    }
}
