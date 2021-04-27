<?php


namespace App\Form;

use App\Entity\Panier;
use App\Entity\PanierProduit;
use App\Entity\Produit;
use Captcha\Bundle\CaptchaBundle\Form\Type\CaptchaType;
use Captcha\Bundle\CaptchaBundle\Validator\Constraints\ValidCaptcha;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Validator\Constraint;


class PanierType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom',TextType::class,['label' => 'Nom de produit : '])
            ->add('prix',TextType::class, ['label' => 'Prix Achat : '])
            ->add('Qte',TextType::class,['label' => 'Stock : '])



        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => PanierProduit::class,
        ]);
    }



}