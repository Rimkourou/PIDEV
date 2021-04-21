<?php

namespace App\Repository;

use App\Entity\SalleDeCinema;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method SalleDeCinema|null find($id, $lockMode = null, $lockVersion = null)
 * @method SalleDeCinema|null findOneBy(array $criteria, array $orderBy = null)
 * @method SalleDeCinema[]    findAll()
 * @method SalleDeCinema[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class SalleDeCinemaRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, SalleDeCinema::class);
    }

    public function findAdminSalle($id)
    {
        $entityManager = $this->getEntityManager();
        return $entityManager->createQuery(
            '
            SELECT s FROM 
             App\Entity\SalleDeCinema s,
             App\Entity\User u
             WHERE s.user = u.id AND u.id = :id
            '
        )->setParameter('id', $id)->getResult();
    }

    public function findAdminSalleByName($id, $nom)
    {
        $entityManager = $this->getEntityManager();
        return $entityManager->createQuery(
            '
            SELECT s FROM 
             App\Entity\SalleDeCinema s,
             App\Entity\User u
             WHERE s.user = u.id AND u.id = :id AND s.nom LIKE :nom
            '
        )->setParameter('id', $id)->setParameter('nom', "%" . $nom . "%")->getResult();
    }
// /**
//  * @return SalleDeCinema[] Returns an array of SalleDeCinema objects
//  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('s.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?SalleDeCinema
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
    public function findMembreSalleByName($nom)
    {
        $entityManager = $this->getEntityManager();
        return $entityManager->createQuery(
            '
            SELECT s FROM 
             App\Entity\SalleDeCinema s
             WHERE s.nom LIKE :nom
            '
        )->setParameter('nom', "%" . $nom . "%")->getResult();
    }
}
