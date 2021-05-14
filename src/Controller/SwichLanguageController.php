<?php

namespace App\Controller;

use phpDocumentor\Reflection\Types\This;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SwichLanguageController extends AbstractController
{
    /**
     * @Route("/change-local/{locale}", name="change_locale")
     * @param $locale
     * @param Request $request
     * @return Response
     */
    public function changeLocale($locale, Request $request): Response
    {
        // on Stock la langue demandée dans la session
        $request->getSession()->set('_locale', $locale);
//        $request->setLocale($locale);
        return $this->redirect($request->headers->get('referer'));
    }

}
