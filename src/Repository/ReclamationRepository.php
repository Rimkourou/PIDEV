<?php

namespace App\Repository;

use App\Entity\Reclamation;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Reclamation|null find($id, $lockMode = null, $lockVersion = null)
 * @method Reclamation|null findOneBy(array $criteria, array $orderBy = null)
 * @method Reclamation[]    findAll()
 * @method Reclamation[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ReclamationRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Reclamation::class);
    }

    // /**
    //  * @return Reclamation[] Returns an array of Reclamation objects
    //  */

    public function findAdminReclamation($id)
    {
        $entityManager = $this->getEntityManager();
        return $entityManager->createQuery(
            '
            SELECT r FROM App\Entity\Reclamation r,
             App\Entity\SalleDeCinema s,
             App\Entity\User u
             WHERE r.salle = s.id AND s.user = u.id AND u.id = :id
            '
        )->setParameter('id', $id)->getResult();
    }

    public function findMembreReclamation($id)
    {
        return $this->findBy([
            'user' => $id
        ]);
    }

    /*
    public function findOneBySomeField($value): ?Reclamation
    {
        return $this->createQueryBuilder('r')
            ->andWhere('r.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
    public function findAdminByState(int $id, string $state)
    {
        $entityManager = $this->getEntityManager();
        return $entityManager->createQuery(
            '
            SELECT r FROM App\Entity\Reclamation r,
             App\Entity\SalleDeCinema s,
             App\Entity\User u
             WHERE r.salle = s.id AND s.user = u.id AND u.id = :id AND r.stete= :state
            '
        )->setParameter('id', $id)->setParameter('state', $state)->getResult();
    }

    public function findAdminByObject(int $id, $objet)
    {
        $entityManager = $this->getEntityManager();
        return $entityManager->createQuery(
            '
            SELECT r FROM App\Entity\Reclamation r,
             App\Entity\SalleDeCinema s,
             App\Entity\User u
             WHERE r.salle = s.id AND s.user = u.id AND u.id = :id AND r.objet LIKE :object
            '
        )->setParameter('id', $id)->setParameter('object', '%' . $objet . '%')->getResult();
    }

    public function findAdminByObjectAndState(int $id, $objet, $state)
    {
        $entityManager = $this->getEntityManager();
        return $entityManager->createQuery(
            '
            SELECT r FROM App\Entity\Reclamation r,
             App\Entity\SalleDeCinema s,
             App\Entity\User u
             WHERE r.salle = s.id AND s.user = u.id AND u.id = :id AND r.objet LIKE :object AND r.stete= :state
            '
        )->setParameter('id', $id)
            ->setParameter('object', '%' . $objet . '%')
            ->setParameter('state', $state)
            ->getResult();
    }

    public function findMembreByState(int $id, $state)
    {
        return $this->findBy([
            'user' => $id,
            'stete' => $state
        ]);
    }

    public function findMembreByObject(int $id, $objet)
    {
        $entityManager = $this->getEntityManager();
        return $entityManager->createQuery(
            '
            SELECT r FROM App\Entity\Reclamation r
             WHERE  r.objet LIKE :object and r.user= :id
            '
        )->setParameter('id', $id)
            ->setParameter('object', '%' . $objet . '%')
            ->getResult();
    }

    public function findMembreByObjectAndState(int $id, $objet, $state)
    {
        $entityManager = $this->getEntityManager();
        return $entityManager->createQuery(
            '
            SELECT r FROM App\Entity\Reclamation r
             WHERE  r.objet LIKE :object AND r.stete= :state AND r.user= :id
            '
        )->setParameter('id', $id)
            ->setParameter('object', '%' . $objet . '%')
            ->setParameter('state', $state)
            ->getResult();
    }
}
