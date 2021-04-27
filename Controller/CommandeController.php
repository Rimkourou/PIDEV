<?php

namespace App\Controller;

use App\Form\CommandeType;
use App\Repository\CommandeRepository;
use App\Repository\ProduitRepository;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;


use Dompdf\Dompdf;
use Dompdf\Options;


class CommandeController extends AbstractController
{
    /**
     * @Route("/commande", name="commande")
     */

    function index(SessionInterface $session, ProduitRepository $produitRepository)
    {
        $panier = $session->get("panier", []);

        // On "fabrique" les données
        $dataPanier = [];
        $total = 0;

        foreach ($panier as $id => $quantite) {
            $product = $produitRepository->find($id);
            $dataPanier[] = [
                "produit" => $product,
                "quantite" => $quantite
            ];
            $total += ($product->getPrix()) * $quantite;
// var_dump($product->getPrix());
        }

        return $this->render('commande/index.html.twig', compact("dataPanier", "total"));
    }


    /**
     * @Route("/payment", name="payment")
     */
    public function payment()
    {
        return $this->render('commande/index.html.twig', [
        ]);
    }


    /**
     * @Route("/success", name="success")
     */
    function success()
    {

        return $this->render("Commande/success.html.twig");
    }


    /**
     * @Route("/error", name="error")
     */
    function error()
    {

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
            'success_url' => $this->generateUrl('commande/success', [], UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url' => $this->generateUrl('commande/error', [], UrlGeneratorInterface::ABSOLUTE_URL),
        ]);

        return new JsonResponse(['id' => $session->id]);
    }

    /**
     * @Route("/Envoyer", name="Envoyer")
     */

    function Envoyer(Request $request, \Swift_Mailer $mailer, ProduitRepository $produitRepository, SessionInterface $session)
    {

        $form = $this->createForm(CommandeType::class);
        $form->handleRequest($request);

        $panier = $session->get("panier", []);

        // On "fabrique" les données
        $dataPanier = [];
        $total = 0;

        foreach ($panier as $id => $quantite) {
            $product = $produitRepository->find($id);
            $dataPanier[] = [
                "produit" => $product,
                "quantite" => $quantite
            ];
            $total += ($product->getPrix()) * $quantite;
// var_dump($product->getPrix());
        }



        if ($form->isSubmitted() && $form->isValid()) {
            $contact = $form->getData();

            $message = (new \Swift_Message('Commande Confirmer'));

            $message->setFrom($contact['email'])
                // On attribue le destinataire
                ->setTo('rim.kourou0@gmail.com')
                // On crée le texte avec la vue
                //                ->setBody('You should see me from the profiler!');
                ->setBody(
                    $this->renderView(
                        'Commande/error.html.twig', compact("dataPanier", "total",'contact')
                    ),
                    'text/html'
                );
//                ->attach(\Swift_Attachment::fromPath('C:/Users/user/Downloads/Facture.pdf'));
            $mailer->send($message);

            $this->addFlash('message', 'Votre message a été transmis, nous vous répondrons dans les meilleurs délais.');


        }
        return $this->render('Commande/success.html.twig', ['CommandeForm' => $form->createView()]);
    }


    /**
     * @Route("/pdf", name="pdf")
     */
    public function pdf(SessionInterface $session, ProduitRepository $produitRepository)
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        $panier = $session->get("panier", []);

        // On "fabrique" les données
        $dataPanier = [];
        $total = 0;

        foreach ($panier as $id => $quantite) {
            $product = $produitRepository->find($id);
            $dataPanier[] = [
                "produit" => $product,
                "quantite" => $quantite
            ];
            $total += ($product->getPrix()) * $quantite;
// var_dump($product->getPrix());
        }


        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('commande/pdf.html.twig', compact("dataPanier", "total")
        );

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("Facture.pdf", [
            "Attachment" => true
        ]);


    }


}
