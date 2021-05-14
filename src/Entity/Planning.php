<?php

namespace App\Entity;

use App\Repository\PlanningRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Table(name="planning")
 * @ORM\Entity(repositoryClass=PlanningRepository::class)
 */
class Planning
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="date")
     * @Assert\NotBlank()
     */
    private $date;

    /**
     * @ORM\Column(type="time")
     * @Assert\NotBlank()
     */
    private $heureDebut;

    /**
     * @ORM\Column(type="time")
     * @Assert\NotBlank()
     */
    private $heureFin;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank()
     */
    private $typeEvent;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank()
     */
    private $titreEvent;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank()
     */
    private $nomSalle;




    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitreEvent(): ?string
    {
        return $this->titreEvent;
    }

    public function setTitreEvent(string $titreEvent): self
    {
        $this->titreEvent = $titreEvent;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function getDayOfDate(): ?string
    {
        return $this->date->format("d");
    }
    public function getMonthOfDate(): ?string
    {
        return $this->date->format("m");
    }
    public function getYearOfDate(): ?string
    {
        return $this->date->format("Y");
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getHeureDebut(): ?\DateTimeInterface
    {
        return $this->heureDebut;
    }

    public function getHeureOfHeureDebut(): ?string
    {
        return $this->heureDebut->format("h");
    }
    public function getMinuteOfHeureDebut(): ?string
    {
        return $this->heureDebut->format("m");
    }
    public function getSecondeOfHeureDebut(): ?string
    {
        return $this->heureDebut->format("s");
    }

    public function setHeureDebut(\DateTimeInterface $heureDebut): self
    {
        $this->heureDebut = $heureDebut;

        return $this;
    }

    public function getHeureFin(): ?\DateTimeInterface
    {
        return $this->heureFin;
    }
    public function getHeureOfHeureFin(): ?string
    {
        return $this->heureFin->format("h");
    }
    public function getMinuteOfHeureFin(): ?string
    {
        return $this->heureFin->format("m");
    }
    public function getSecondeOfHeureFin(): ?string
    {
        return $this->heureFin->format("s");
    }

    public function setHeureFin(\DateTimeInterface $heureFin): self
    {
        $this->heureFin = $heureFin;

        return $this;
    }

    public function getTypeEvent(): ?string
    {
        return $this->typeEvent;
    }

    public function setTypeEvent(string $typeEvent): self
    {
        $this->typeEvent = $typeEvent;

        return $this;
    }

    public function getNomSalle(): ?string
    {
        return $this->nomSalle;
    }

    public function setNomSalle(?string $nomSalle): self
    {
        $this->nomSalle = $nomSalle;
        return $this;
    }
}
