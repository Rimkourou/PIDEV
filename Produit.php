<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=ProduitRepository::class)
 */
class Produit
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     *@Assert\NotBlank(message="valider")

     */
    private $nom;

    /**
     * @ORM\Column(type="float")
     *@Assert\NotBlank(message="valider")

     *
     */
    private $prix;

    /**
     * @ORM\Column(type="integer")
     *@Assert\NotBlank(message="valider")
     *@Assert\Length(
     *min=3,
     *max=50,
     *minMessage="Lenom de la qte doit comporter au moins {{ limit }} caractères",
     *maxMessage ="Le nom de la qte doit comporter au plus {{ limit }} caractères"
     *
    )
     */
    private $Qte;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="valider")

     */
    private $idCategorie;

    /**
     * @ORM\Column(type="string", length=255)


     */
    private $image;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
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

    public function getIdCategorie(): ?string
    {
        return $this->idCategorie;
    }

    public function setIdCategorie(string $idCategorie): self
    {
        $this->idCategorie = $idCategorie;

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
