<?php

namespace App\Form;

use App\Entity\SalleDeCinema;
use App\Entity\User;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class SalleDeCinemaType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom')
            ->add('nbrPlace')
            ->add('Description')
            ->add('adresse')
//            ->add('user', EntityType::class,['class' => User::class,
//                'choice_label' => 'nom',
//                'label' => 'user'])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => SalleDeCinema::class,
        ]);
    }
}
