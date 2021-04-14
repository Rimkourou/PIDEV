<?php

namespace App\Form;

use App\Entity\Reclamation;
use App\Entity\SalleDeCinema;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReclamationMembreType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('objet')
            ->add('description')
//            ->add('stete')
            ->add('salle', EntityType::class, ['class' => SalleDeCinema::class,
                'choice_label' => 'nom',
                'label' => 'salleDeCinema'])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
        ]);
    }
}
