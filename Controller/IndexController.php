<?php

namespace App\Controller;

use App\Entity\Book;
use App\Entity\PropertySearch;
use App\Form\PropertySearchType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class IndexController extends AbstractController
{
    /**
     * @Route("/",name="indexController")
     */
    public function index(Request $request): Response
    {

        return $this->render('index.html.twig');
    }

}
