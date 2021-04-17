<?php

namespace App\Entity;

use App\Repository\ReservationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=ReservationRepository::class)
 */
class Reservation
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="date")
     * @Assert\NotBlank (message="please enter the movie room")
     */
    private $datereservation;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank (message="please enter your name")
     * @Assert\Length (min=4, max=10, minMessage = "your name must be at least {{ limit }} characters long",maxMessage = "Your name cannot be longer than {{ limit }} characters")
     */
    private $iduser;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank (message="please enter the movie room")
     */
    private $idsalle;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank (message="please insert the movie name")
     */
    private $idfilm;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank (message="please insert the number of places to book!")
     * @Assert\Positive
     * @Assert\Range(
     *      min = 1,
     *      max = 5,
     *      notInRangeMessage = "You must select between {{ min }}places and {{ max }}places to enter",)
     */
    private $nbrplaceres;

    /*/**
     * @ORM\Column(type="string", length=255)
     */
    //private $idspectacle;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateReservation(): ?\DateTimeInterface
    {
        return $this->datereservation;
    }

    public function setDateReservation(\DateTimeInterface $datereservation): self
    {
        $this->datereservation = $datereservation;

        return $this;
    }

    public function getIdUser(): ?string
    {
        return $this->iduser;
    }

    public function setIdUser(string $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }

    public function getIdSalle(): ?string
    {
        return $this->idsalle;
    }

    public function setIdSalle(string $idsalle): self
    {
        $this->idsalle = $idsalle;

        return $this;
    }

    public function getIdFilm(): ?string
    {
        return $this->idfilm;
    }

    public function setIdFilm(string $idfilm): self
    {
        $this->idfilm = $idfilm;

        return $this;
    }

    public function getNbrPlaceRes(): ?int
    {
        return $this->nbrplaceres;
    }

    public function setNbrPlaceRes(int $nbrplaceres): self
    {
        $this->nbrplaceres = $nbrplaceres;

        return $this;
    }

    /*public function getIdSpectacle(): ?string
    {
        return $this->idspectacle;
    }

    public function setIdSpectacle(string $idspectacle): self
    {
        $this->idspectacle = $idspectacle;

        return $this;
    }*/
}
