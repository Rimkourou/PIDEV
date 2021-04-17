<?php

namespace App\Entity;

use App\Repository\FilmRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=FilmRepository::class)
 */
class Film
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank (message="please insert the title")
     * @Assert\Length (min=5, max=10, minMessage = "Movie name must be at least {{ limit }} characters long",maxMessage = "Movie name cannot be longer than {{ limit }} characters")
     */
    private $titre;

    /**
     * @ORM\Column(type="text")
     * @Assert\NotBlank (message="please insert the description")
     */
    private $description;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank (message="please insert the author")
     * @Assert\Length (min=4, max=10, minMessage = "Athor name must be at least {{ limit }} characters long",maxMessage = "Author name cannot be longer than {{ limit }} characters")
     */
    private $auteur;

    /**
     * @ORM\Column(type="string", length=255, columnDefinition="ENUM('Long Movie', 'Short Movie')")
     */
    private $categorie;

    /**
     * @ORM\Column(type="string", length=255, columnDefinition="ENUM('Horror', 'Drama', 'Action', 'Animation', 'Documentary')")
     */
    private $genre;

    /**
     * @Assert\NotBlank(message="please enter an image")
     * @Assert\Image()
     * @ORM\Column(type="string", length=255)
     */
    private $img;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): self
    {
        $this->titre = $titre;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getAuteur(): ?string
    {
        return $this->auteur;
    }

    public function setAuteur(string $auteur): self
    {
        $this->auteur = $auteur;

        return $this;
    }

    public function getCategorie(): ?string
    {
        return $this->categorie;
    }

    public function setCategorie(string $categorie): self
    {
        $this->categorie = $categorie;

        return $this;
    }

    public function getGenre(): ?string
    {
        return $this->genre;
    }

    public function setGenre(string $genre): self
    {
        $this->genre = $genre;

        return $this;
    }

    public function getImg(): ?string
    {
        return $this->img;
    }

    public function setImg(string $img): self
    {
        $this->img = $img;

        return $this;
    }
}
