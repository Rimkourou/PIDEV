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
     * @ORM\Column(name="dateReservation", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $datereservation = 'CURRENT_TIMESTAMP';

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


}
