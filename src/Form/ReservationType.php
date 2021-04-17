<?php

namespace App\Form;

use App\Entity\Reservation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReservationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('datereservation', DateType::class, [
                'label' => 'Reservation Date'])

            ->add('iduser', TextType::class, [
                'label' => 'User',
                'attr' => [
                    'placeholder' => 'Your name'
                ]
            ])
            ->add('idsalle', TextType::class, [
                'label' => 'Movie Room',
                'attr' => [
                    'placeholder' => 'Movie Room'
                ]
            ])
            ->add('idfilm', TextType::class, [
                'label' => 'Movie',
                'attr' => [
                    'placeholder' => 'Movie'
                ]
            ])
            ->add('nbrplaceres', TextType::class, [
                'label' => 'Places',
                'attr' => [
                    'placeholder' => 'Places'
                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Reservation::class,
        ]);
    }
}
