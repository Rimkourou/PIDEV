<?php


namespace App\Controller;


use App\Form\CommandeType;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class PaymentController extends AbstractController
{

    /**
     * @Route("/payment", name="payment")
     */
    public function index()
    {
        return $this->render('commande/index.html.twig', [
        ]);
    }

    /**
     * @Route("/success", name="success")
     */
    function success (){

        return $this->render("Commande/success.html.twig");
    }


    /**
     * @Route("/error", name="error")
     */
    function error (){

        return $this->render("Commande/error.html.twig");
    }





    /**
     * @Route("/create-checkout-session", name="checkout")
     */
    public function checkout()
    {
        \Stripe\Stripe::setApiKey('sk_test_51IhkrqHkmBI45FCuLCL0OmszQyiVIzaGgAHVTgrGjVbWfuS5gBQ7LQKECmDdwP1xwM8DVj2Rd3rlGrgKq93N1pRw00pjnnr5LM');
        $session = \Stripe\Checkout\Session::create([
            'payment_method_types' => ['card'],
            'line_items' => [[
                'price_data' => [
                    'currency' => 'eur',
                    'product_data' => [
                        'name' => 'T-shirt',
                    ],
                    'unit_amount' => 2000,
                ],
                'quantity' => 1,
            ]],
            'mode' => 'payment',
            'success_url' => $this->generateUrl('success', [], UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url' => $this->generateUrl('error', [], UrlGeneratorInterface::ABSOLUTE_URL),
        ]);

        return new JsonResponse(['id' => $session->id ]);
    }



}