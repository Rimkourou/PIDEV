<?php

namespace App\Form;

use App\Entity\Film;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class FilmType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('titre', TextType::class, [
                'label' => 'Title',
                'attr' => [
                    'placeholder' => 'movie title'
                ]
            ])
            ->add('description', TextType::class, [
                'label' => 'Description',
                'attr' => [
                    'placeholder' => 'movie description'
                ]
            ])
            ->add('auteur', TextType::class, [
                'label' => 'Author',
                'attr' => [
                    'placeholder' => 'movie author'
                ]
            ])
            ->add('categorie', ChoiceType::class, ['choices'  => [
                'Long Movie' => 'Long Movie',
                'Short Movie' => 'Short Movie',]
            ])
            ->add('genre', ChoiceType::class, ['choices'  => [
                'Horror' => 'Horror',
                'Drama' => 'Drama',
                'Action' => 'Action',
                'Animation' => 'Animation',
                'Documentary' => 'Documentary',]
            ])
            ->add('img', FileType::class,array('label'=>'insert an image','data_class' => null)
            );
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Film::class,
        ]);
    }
}
