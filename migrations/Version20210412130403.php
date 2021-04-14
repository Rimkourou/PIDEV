<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210412130403 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE reclamation (id INT AUTO_INCREMENT NOT NULL, user_id INT DEFAULT NULL, salle_id INT DEFAULT NULL, objet VARCHAR(255) NOT NULL, description LONGTEXT NOT NULL, stete VARCHAR(255) NOT NULL, INDEX IDX_CE606404A76ED395 (user_id), INDEX IDX_CE606404DC304035 (salle_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE salle_de_cinema (id INT AUTO_INCREMENT NOT NULL, user_id INT NOT NULL, nom VARCHAR(255) NOT NULL, nbr_place INT NOT NULL, description LONGTEXT NOT NULL, adresse VARCHAR(255) NOT NULL, INDEX IDX_28F53AC0A76ED395 (user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, age DATE NOT NULL, role VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, state VARCHAR(255) NOT NULL, s_ques VARCHAR(255) NOT NULL, answer VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT FK_CE606404A76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT FK_CE606404DC304035 FOREIGN KEY (salle_id) REFERENCES salle_de_cinema (id)');
        $this->addSql('ALTER TABLE salle_de_cinema ADD CONSTRAINT FK_28F53AC0A76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE reclamation DROP FOREIGN KEY FK_CE606404DC304035');
        $this->addSql('ALTER TABLE reclamation DROP FOREIGN KEY FK_CE606404A76ED395');
        $this->addSql('ALTER TABLE salle_de_cinema DROP FOREIGN KEY FK_28F53AC0A76ED395');
        $this->addSql('DROP TABLE reclamation');
        $this->addSql('DROP TABLE salle_de_cinema');
        $this->addSql('DROP TABLE user');
    }
}
