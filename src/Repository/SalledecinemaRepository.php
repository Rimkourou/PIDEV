<?php

namespace App\Repository;

use App\Entity\Salledecinema;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Salledecinema|null find($id, $lockMode = null, $lockVersion = null)
 * @method Salledecinema|null findOneBy(array $criteria, array $orderBy = null)
 * @method Salledecinema[]    findAll()
 * @method Salledecinema[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class SalledecinemaRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Salledecinema::class);
    }

    // /**
    //  * @return Salledecinema[] Returns an array of Salledecinema objects
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
    public function findOneBySomeField($value): ?Salledecinema
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
