<?php

namespace App\Entity;

use App\Repository\PromotionRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * @ORM\Entity(repositoryClass=PromotionRepository::class)
 */
class Promotion
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;
    /**
     *@ORM\Column(type="text")
     * @Assert\NotBlank(message="veuillez entrer un critére")
     */
    private $critaire;
    /**
     *@ORM\Column(type="date")
     */
    private $dated;
    /**
     *@ORM\Column(type="date")
     */
    private $datef;
    /**
     *@ORM\Column(type="integer")
     *  @Assert\NotBlank
     */
    private $iduser;
    /**
     *@ORM\Column(type="string")
     *  @Assert\Image()
     */
    private $castphoto;
    /**
     *@ORM\Column(type="string")
     *  @Assert\NotBlank()
     */
    private $reduction;

    public function getId(): ?int
    {
        return $this->id;
    }


    /**
     * @return mixed
     */
    public function getCritaire()
    {
        return $this->critaire;
    }

    /**
     * @param mixed $critaire
     */
    public function setCritaire($critaire): void
    {
        $this->critaire = $critaire;
    }

    /**
     * @return mixed
     */
    public function getDated()
    {
        return $this->dated;
    }

    /**
     * @param mixed $dated
     */
    public function setDated($dated): void
    {
        $this->dated = $dated;
    }

    /**
     * @return mixed
     */
    public function getDatef()
    {
        return $this->datef;
    }

    /**
     * @param mixed $datef
     */
    public function setDatef($datef): void
    {
        $this->datef = $datef;
    }

    /**
     * @return mixed
     */
    public function getIduser()
    {
        return $this->iduser;
    }

    /**
     * @param mixed $iduser
     */
    public function setIduser($iduser): void
    {
        $this->iduser = $iduser;
    }

    /**
     * @return mixed
     */
    public function getCastphoto()
    {
        return $this->castphoto;
    }

    /**
     * @param mixed $castphoto
     */
    public function setCastphoto($castphoto): void
    {
        $this->castphoto = $castphoto;
    }



    /**
     * @return mixed
     */
    public function getReduction()
    {
        return $this->reduction;
    }

    /**
     * @param mixed $reduction
     */
    public function setReduction($reduction): void
    {
        $this->reduction = $reduction;
    }

}
