<?php

namespace App\DataFixtures;

use App\Entity\User;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Persistence\ObjectManager;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

class UserFixture extends Fixture
{
    private $encoder;

    /**
     * UserFixture constructor.
     * @param $encoder
     */
    public function __construct(UserPasswordEncoderInterface $encoder)
    {
        $this->encoder = $encoder;
    }

    public function load(ObjectManager $manager )
    {
       $user=new User();
       $user->setNom('admin');
       $user->setPassword(
           $this->encoder->encodePassword($user,'123')
       );
       $user->setEmail('ons.driss@esprit.tn');
       $user->setPrenom('admin');
       $user->setRole('admin');
       $user->setState('active');

        $date = "01-09-2015";
       $user->setAge(new \DateTime($date));

        $manager->persist($user);

        $manager->flush();
    }
}
