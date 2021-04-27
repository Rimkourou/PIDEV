<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;



/**
 * Produit
 *
 * @ORM\Table(name="produit", indexes={@ORM\Index(name="idCategorie", columns={"idCategorie"})})
 * @ORM\Entity
 */
class Produit
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var string|null
     *
     * @ORM\Column(name="nom", type="string", length=255, nullable=true)
     */
    private $nom;

    /**
     * @var float
     *
     * @ORM\Column(name="prix", type="float", precision=10, scale=0, nullable=false)
     */
    private $prix;

    /**
     * @var int
     *
     * @ORM\Column(name="Qte", type="integer", nullable=false)
     */
    private $qte;


    /**
     * @var string|null
     *
     * @ORM\Column(name="image", type="string", length=255, nullable=true)
     */
    private $image;

    /**
     * @ORM\ManyToOne(targetEntity=Categorie::class, inversedBy="produits")
     * @ORM\JoinColumn(name="idcategorie")
     */

    private $categorie;

    /**
     * @ORM\ManyToMany(targetEntity=Panier::class, mappedBy="Produit")
     */
    private $paniers;

    public function __construct()
    {
        $this->paniers = new ArrayCollection();
    }

    /**
     * @return int
     */
    public function getId(): int
    {
        return $this->id;
    }

    /**
     * @param int $id
     */
    public function setId(int $id): void
    {
        $this->id = $id;
    }

    /**
     * @return string|null
     */
    public function getNom(): ?string
    {
        return $this->nom;
    }

    /**
     * @param string|null $nom
     */
    public function setNom(?string $nom): void
    {
        $this->nom = $nom;
    }

    /**
     * @return float
     */
    public function getPrix(): ?float
    {
        return $this->prix;
    }

    /**
     * @param float $prix
     */
    public function setPrix(float $prix): void
    {
        $this->prix = $prix;
    }

    /**
     * @return int
     */
    public function getQte(): ?int
    {
        return $this->qte;
    }

    /**
     * @param int $qte
     */
    public function setQte(int $qte): void
    {
        $this->qte = $qte;
    }


    /**
     * @return string|null
     */
    public function getImage(): ?string
    {
        return $this->image;
    }

    /**
     * @param string|null $image
     */
    public function setImage(?string $image): void
    {
        $this->image = $image;
    }

    public function getCategorie(): ?Categorie
    {
        return $this->categorie;
    }

    public function setCategorie(?Categorie $categorie): self
    {
        $this->categorie = $categorie;

        return $this;
    }





//    /**
//     * @return Collection|Panier[]
//     */
//    public function getPanier(): Collection
//    {
//        return $this->panier;
//    }
//
//    public function addPanier(Panier $panier): self
//    {
//        if (!$this->panier->contains($panier)) {
//            $this->panier[] = $panier;
//            $panier->addProduit($this);
//        }
//
//        return $this;
//    }
//
//    public function removePanier(Panier $panier): self
//    {
//        if ($this->panier->removeElement($panier)) {
//            $panier->removeProduit($this);
//        }
//
//        return $this;
//    }

/**
 * @return Collection|Panier[]
 */
public function getPaniers(): Collection
{
    return $this->paniers;
}

public function addPanier(Panier $panier): self
{
    if (!$this->paniers->contains($panier)) {
        $this->paniers[] = $panier;
        $panier->addProduit($this);
    }

    return $this;
}

public function removePanier(Panier $panier): self
{
    if ($this->paniers->removeElement($panier)) {
        $panier->removeProduit($this);
    }

    return $this;
}
}
