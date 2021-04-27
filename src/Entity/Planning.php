<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Planning
 *
 * @ORM\Table(name="planning", indexes={@ORM\Index(name="idFilm", columns={"idFilm"}), @ORM\Index(name="idSpectacle", columns={"idSpectacle"})})
 * @ORM\Entity
 */
class Planning
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
     * @ORM\Column(name="date", type="date", nullable=false)
     */
    private $date;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="heureDebut", type="time", nullable=false)
     */
    private $heuredebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="heureFin", type="time", nullable=false)
     */
    private $heurefin;

    /**
     * @var int
     *
     * @ORM\Column(name="idFilm", type="integer", nullable=false)
     */
    private $idfilm;

    /**
     * @var int
     *
     * @ORM\Column(name="idSpectacle", type="integer", nullable=false)
     */
    private $idspectacle;

    /**
     * @var int
     *
     * @ORM\Column(name="idSalle", type="integer", nullable=false)
     */
    private $idsalle;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getHeuredebut(): ?\DateTimeInterface
    {
        return $this->heuredebut;
    }

    public function setHeuredebut(\DateTimeInterface $heuredebut): self
    {
        $this->heuredebut = $heuredebut;

        return $this;
    }

    public function getHeurefin(): ?\DateTimeInterface
    {
        return $this->heurefin;
    }

    public function setHeurefin(\DateTimeInterface $heurefin): self
    {
        $this->heurefin = $heurefin;

        return $this;
    }

    public function getIdfilm(): ?int
    {
        return $this->idfilm;
    }

    public function setIdfilm(int $idfilm): self
    {
        $this->idfilm = $idfilm;

        return $this;
    }

    public function getIdspectacle(): ?int
    {
        return $this->idspectacle;
    }

    public function setIdspectacle(int $idspectacle): self
    {
        $this->idspectacle = $idspectacle;

        return $this;
    }

    public function getIdsalle(): ?int
    {
        return $this->idsalle;
    }

    public function setIdsalle(int $idsalle): self
    {
        $this->idsalle = $idsalle;

        return $this;
    }


}
