<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Planning
 *
 * @ORM\Table(name="planning", indexes={@ORM\Index(name="idFilm", columns={"idFilm"})})
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


}
