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
}
