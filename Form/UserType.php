<?php

namespace App\Form;

use App\Entity\User;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;


class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom', TextType::class, [
                'attr'=>[
                    'placeholder' =>'Enter your Name'
                ]
            ])
            ->add('prenom', TextType::class, [
                'attr'=>[
                    'placeholder' =>'Enter your LastName'
                ]
            ])
            ->add('age')
            ->add('role', TextType::class, [
                'attr'=>[
                    'placeholder' =>'Enter your Role'
                ]
            ])
            ->add('email', TextType::class, [
                'attr'=>[
                    'placeholder' =>'Enter your Email'
                ]
            ])
            ->add('password', TextType::class,[
                'attr'=>['placeholder' =>'Enter your Password']
            ])
            ->add('state', TextType::class, [
                'attr'=>[
                    'placeholder' =>'Enter your State'
                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
