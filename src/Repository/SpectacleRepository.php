<?php

namespace App\Repository;

use App\Entity\Spectacle;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Spectacle|null find($id, $lockMode = null, $lockVersion = null)
 * @method Spectacle|null findOneBy(array $criteria, array $orderBy = null)
 * @method Spectacle[]    findAll()
 * @method Spectacle[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class SpectacleRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Spectacle::class);
    }
public function orderByDate(){
        return $this->createQueryBuilder('s')
            ->orderBy('s.date', 'ASC')
            ->getQuery()->getResult();
    }
    // /**
    //  * @return Spectacle[] Returns an array of Spectacle objects
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
    public function findOneBySomeField($value): ?Spectacle
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */

//    public function findSpecTitle($value)
//    {
//        return $this->createQueryBuilder('s')
//            ->select('s.titre')
//            ->andWhere('s.id = :val')
//            ->setParameter('val', $value)
//            ->orderBy('s.id', 'ASC')
//            ->getQuery()
//            ->getResult()
//            ;
//    }

    public function findSpecTitle()
    {
        return $this->createQueryBuilder('s')
            ->select('s.titre')
            ->orderBy('s.id', 'ASC')
            ->getQuery()
            ->getResult()
            ;
    }
}
