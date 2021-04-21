<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * PanierProduit
 *
 * @ORM\Table(name="panier_produit")
 * @ORM\Entity
 */
class PanierProduit
{
    /**
     * @var int
     *
     * @ORM\Column(name="idPanier", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idpanier;

    /**
     * @var int
     *
     * @ORM\Column(name="idProduit", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idproduit;

    /**
     * @var int
     *
     * @ORM\Column(name="Qte", type="integer", nullable=false)
     */
    private $qte;


}
