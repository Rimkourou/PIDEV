<?php

namespace App\Form;

use App\Entity\Reclamation;
use App\Entity\SalleDeCinema;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReclamationEditAdminType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
//            ->add('objet')

            ->add('stete', ChoiceType::class, [
                'choices' => [
                    'pas encore' => 'pas encore',
                    'en cours' => 'en cours',
                    'resolu' => 'resolu',
                ],])
//            ->add('description ')
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
        ]);
    }
}
