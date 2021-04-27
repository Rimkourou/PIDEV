<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Reservation
 *
 * @ORM\Table(name="reservation", indexes={@ORM\Index(name="idUser", columns={"idUser"})})
 * @ORM\Entity
 */
class Reservation
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
     * @var \DateTime
     *
     * @ORM\Column(name="dateReservation", type="datetime", nullable=false, options={"default"="current_timestamp()"})
     */
    private $datereservation = 'current_timestamp()';

    /**
     * @var int
     *
     * @ORM\Column(name="idUser", type="integer", nullable=false)
     */
    private $iduser;

    /**
     * @var string
     *
     * @ORM\Column(name="idSalle", type="string", length=255, nullable=false)
     */
    private $idsalle;

    /**
     * @var string
     *
     * @ORM\Column(name="idFilm", type="string", length=255, nullable=false)
     */
    private $idfilm;

    /**
     * @var int
     *
     * @ORM\Column(name="nbrPlaceRes", type="integer", nullable=false)
     */
    private $nbrplaceres;

    /**
     * @var string
     *
     * @ORM\Column(name="idSpectacle", type="string", length=255, nullable=false)
     */
    private $idspectacle;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDatereservation(): ?\DateTimeInterface
    {
        return $this->datereservation;
    }

    public function setDatereservation(\DateTimeInterface $datereservation): self
    {
        $this->datereservation = $datereservation;

        return $this;
    }

    public function getIduser(): ?int
    {
        return $this->iduser;
    }

    public function setIduser(int $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }

    public function getIdsalle(): ?string
    {
        return $this->idsalle;
    }

    public function setIdsalle(string $idsalle): self
    {
        $this->idsalle = $idsalle;

        return $this;
    }

    public function getIdfilm(): ?string
    {
        return $this->idfilm;
    }

    public function setIdfilm(string $idfilm): self
    {
        $this->idfilm = $idfilm;

        return $this;
    }

    public function getNbrplaceres(): ?int
    {
        return $this->nbrplaceres;
    }

    public function setNbrplaceres(int $nbrplaceres): self
    {
        $this->nbrplaceres = $nbrplaceres;

        return $this;
    }

    public function getIdspectacle(): ?string
    {
        return $this->idspectacle;
    }

    public function setIdspectacle(string $idspectacle): self
    {
        $this->idspectacle = $idspectacle;

        return $this;
    }


}
