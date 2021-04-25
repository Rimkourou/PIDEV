<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Exception;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Security\Core\User\UserInterface;

/**
 * @ORM\Entity(repositoryClass=UserRepository::class)
 * @UniqueEntity(fields="email", message="Email already taken")
 * @ORM\Table(name="user", indexes={@ORM\Index(columns={"nom","prenom","email"}, flags={"fulltext"})})
 */
class User implements UserInterface, \Serializable
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;
    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="veuillez entrer votre Nom")
     */
    private $nom;
    /**
     * @ORM\Column(type="string" , length=255)
     * @Assert\NotBlank(message="veuillez entrer votre Prenom")
     */
    private $prenom;
    /**
     * @ORM\Column(type="date")
     */
    private $age;
    /**
     * @ORM\Column(type="string" , length=255)
     */
    private $role;
    /**
     * @ORM\Column(type="string" , length=255 ,unique=true)
     * @Assert\NotBlank()
     * @Assert\Email()
     */
    private $email;
    /**
     * @ORM\Column(type="string" , length=255)
     * @Assert\NotBlank()
     */
    private $password;
    /**
     * @Assert\Length(max=4096)
     */
    private $plainPassword;
    /**
     * @ORM\Column(type="string" , length=255)
     */
    private $state;
    /**
     * @ORM\Column(type="string" , length=255)
     */
    private $resettoken;
    /**
     * @ORM\Column(type="string" , length=255)
     */
    private $token;

    /**
     * @ORM\OneToMany(targetEntity=Salledecinema::class, mappedBy="user", orphanRemoval=true)
     */
    private $salleDeCinemas;

    /**
     * @ORM\OneToMany(targetEntity=Reclamation::class, mappedBy="user")
     */
    private $reclamations;

    public function __construct()
    {
        $this->salleDeCinemas = new ArrayCollection();
        $this->reclamations = new ArrayCollection();
    }

    /**
     * @return Collection|Salledecinema[]
     */
    public function getSalleDeCinemas(): Collection
    {
        return $this->salleDeCinemas;
    }

    public function addSalleDeCinema(Salledecinema $salleDeCinema): self
    {
        if (!$this->salleDeCinemas->contains($salleDeCinema)) {
            $this->salleDeCinemas[] = $salleDeCinema;
            $salleDeCinema->setUser($this);
        }

        return $this;
    }

    public function removeSalleDeCinema(Salledecinema $salleDeCinema): self
    {
        if ($this->salleDeCinemas->removeElement($salleDeCinema)) {
            // set the owning side to null (unless already changed)
            if ($salleDeCinema->getUser() === $this) {
                $salleDeCinema->setUser(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|Reclamation[]
     */
    public function getReclamations(): Collection
    {
        return $this->reclamations;
    }

    public function addReclamation(Reclamation $reclamation): self
    {
        if (!$this->reclamations->contains($reclamation)) {
            $this->reclamations[] = $reclamation;
            $reclamation->setUser($this);
        }

        return $this;
    }

    public function removeReclamation(Reclamation $reclamation): self
    {
        if ($this->reclamations->removeElement($reclamation)) {
            // set the owning side to null (unless already changed)
            if ($reclamation->getUser() === $this) {
                $reclamation->setUser(null);
            }
        }

        return $this;
    }
    public function getId(): ?int
    {
        return $this->id;
    }

    /**
     * @return mixed
     */
    public function getNom()
    {
        return $this->nom;
    }

    /**
     * @param mixed $nom
     */
    public function setNom($nom): void
    {
        $this->nom = $nom;
    }

    /**
     * @return mixed
     */
    public function getPrenom()
    {
        return $this->prenom;
    }

    /**
     * @param mixed $prenom
     */
    public function setPrenom($prenom): void
    {
        $this->prenom = $prenom;
    }

    /**
     * @return mixed
     */
    public function getAge()
    {
        return $this->age;
    }

    /**
     * @param mixed $age
     */
    public function setAge($age): void
    {
        $this->age = $age;
    }

    /**
     * @return mixed
     */
    public function getRole()
    {
        return $this->role;
    }

    /**
     * @param mixed $role
     */
    public function setRole($role): void
    {
        $this->role = $role;
    }

    /**
     * @return mixed
     */
    public function getEmail()
    {
        return $this->email;
    }

    /**
     * @param mixed $email
     */
    public function setEmail($email): void
    {
        $this->email = $email;
    }

    /**
     * @return mixed
     */
    public function getPassword()
    {
        return $this->password;
    }

    /**
     * @param mixed $password
     */
    public function setPassword($password): void
    {
        $this->password = $password;
    }

    /**
     * @return mixed
     */
    public function getState()
    {
        return $this->state;
    }

    /**
     * @param mixed $state
     */
    public function setState($state): void
    {
        $this->state = $state;
    }

    /**
     * @return mixed
     */
    public function getPlainPassword()
    {
        return $this->plainPassword;
    }

    /**
     * @param mixed $plainPassword
     */
    public function setPlainPassword($plainPassword): void
    {
        $this->plainPassword = $plainPassword;
    }

    /**
     * @return mixed
     */
    public function getToken()
    {
        return $this->token;
    }

    /**
     * @param mixed $token
     */
    public function setToken($token): void
    {
        $this->token = $token;
    }

    /**
     * @return mixed
     */
    public function getResettoken()
    {
        return $this->resettoken;
    }

    /**
     * @param mixed $resettoken
     */
    public function setResettoken($resettoken): void
    {
        $this->resettoken = $resettoken;
    }


    public function getRoles()
    {
        return [
            'ROLE_USER'
        ];
    }

    public function getSalt()
    {
        // TODO: Implement getSalt() method.
    }

    public function getUsername()
    {
        // TODO: Implement getUsername() method.
    }

    public function eraseCredentials()
    {
        // TODO: Implement eraseCredentials() method.
    }

    public function serialize()
    {
        return serialize([
            $this->id,
            $this->nom,
            $this->prenom,
            $this->age,
            $this->role,
            $this->email,
            $this->password
        ]);
    }

    public function unserialize($serialized)
    {
        list(
            $this->id,
            $this->nom,
            $this->prenom,
            $this->age,
            $this->role,
            $this->email,
            $this->password
            ) = unserialize($serialized, ['allowed_classes' => false]);
    }

}
