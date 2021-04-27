<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use PanierBundle\Entity\ProduitPanier;

/**
 * PanierProduit
 *
 * @ORM\Table(name="panier_produit", indexes={@ORM\Index(name="produit_id", columns={"produit_id"}), @ORM\Index(name="panier_id", columns={"panier_id"})})
 * @ORM\Entity
 */
class PanierProduit
{
    /**
     * @var int
     *
     * @ORM\Column(name="panier_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $panierId;

    /**
     * @var int
     *
     * @ORM\Column(name="produit_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $produitId;

    /**
     * @ORM\Column(type="integer")
     */
    private $prix;

    /**
     * @ORM\Column(type="integer")
     */
    private $Qte;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $nom;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $image;

    public function getPanierId(): ?int
    {
        return $this->panierId;
    }

    public function getProduitId(): ?int
    {
        return $this->produitId;
    }


    /**
     * PanierProduit newPanierProduit.
     * @param int $PanierId;
     * @param int $ProduitId;
     * @param int $prix;
     * @param int $Qte;
     * @param string $nom;
     * @param string $image;

     * @return ProduitPanier
     */
    public function newPanierProduit (int $PanierId ,int $ProduitId ,int $prix ,int $Qte ,string $nom ,string $image)
    {
        $this->PanierId = $PanierId;
        $this->ProduitId = $ProduitId;
        $this->prix = $prix;
        $this ->Qte= $Qte;
        $this -> nom = $nom;
        $this -> image = $image;

        return $this;
    }

    public function getPrix(): ?int
    {
        return $this->prix;
    }

    public function setPrix(int $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getQte(): ?int
    {
        return $this->Qte;
    }

    public function setQte(int $Qte): self
    {
        $this->Qte = $Qte;

        return $this;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }

}
