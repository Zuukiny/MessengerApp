🧱 ConnectionFactory = Host at the Front Desk

    Greets customers as they arrive (clients connecting).

    Assigns them a table (accepts the connection).

    Passes them off to a waiter for service (delegates to ConnectionHandler).

📄 ConnectionHandler = Waiter Job Description

    Describes how the customer will be served:

        “Take their order, bring food, handle payment.”

🍲 EchoServer = Specific Waiter Who Only Repeats Your Order

    Implements the job, but with a quirky behavior:

        You say, “One burger,” and they repeat, “One burger,” and leave — that’s it.