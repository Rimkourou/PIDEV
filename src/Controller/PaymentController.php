<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
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
        return $this->render('payment/index.html.twig', [
        ]);
    }

    /**
     * @Route("/error", name="error")
     */
    public function error()
    {
        return $this->render('payment/error.html.twig', [
        ]);
    }

    /**
     * @Route("/create-checkout-session", name="checkout")
     */
    public function checkout()
    {
        \Stripe\Stripe::setApiKey('sk_test_51IhZSmEUbBuxKZQLs0mYFkw4rIcSWjj6cwsu5Mts2udwn89ClKPgwwQTjZ2v3bfanvzBjI71WMHYwadEXDqoctIE00q5ADsYY3');
        $session = \Stripe\Checkout\Session::create([
            'payment_method_types' => ['card'],
            'line_items' => [[
                'price_data' => [
                    'currency' => 'usd',
                    'product_data' => [
                        'name' => 'Ticket',
                    ],
                    'unit_amount' => 200,
                ],
                'quantity' => 1,
            ]],
            'mode' => 'payment',
            'locale'=>'en',
            'success_url' => $this->generateUrl('reservationList', [], UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url' => $this->generateUrl('error', [], UrlGeneratorInterface::ABSOLUTE_URL),
        ]);
        return new JsonResponse([ 'id' => $session->id ]);
    }
}
