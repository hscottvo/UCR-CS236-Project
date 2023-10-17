# UCR-CS236-Project

- [UCR-CS236-Project](#ucr-cs236-project)
  - [Setup](#setup)
    - [Docker Setup](#docker-setup)
    - [Project Setup](#project-setup)
    - [Getting Data](#getting-data)
  - [Running Code](#running-code)
  - [Project Info](#project-info)
    - [Getting Started](#getting-started)
    - [Task 1](#task-1)
    - [Task 2](#task-2)

For this project, you will be using [Spark](https://spark.apache.org/), a big data framework that allows developers to process massive amounts of data with parallelism and fault tolerance included by default. You will be utilizing [Docker](https://www.docker.com/) to run this on your local machine.

## Setup

In this project, the only setup required is:

- Docker
- a terminal to run shell commands
- a web browser to access localhost

### Docker Setup

Go to [the Docker website](https://www.docker.com/get-started/), download the installer, and install Docker with the default configuration. If you are on Windows, it is highly recommended that you use Docker Desktop's WSL-based engine ([instructions here](https://docs.docker.com/desktop/wsl/)). Regardless, it will ask you to restart. Do this and make sure Docker is opened before continuing.

### Project Setup

Simply clone this repository and cd into it. You will see `data/`, which will be where you put your data files. You will also see `src/`, where your notebooks will be.

### Getting Data

In this project you will be using data from the USDA's Food Environment Atlas.

[Data Source](https://www.ers.usda.gov/data-products/food-environment-atlas/data-access-and-documentation-downloads/)

Scroll down to `Data Set -> Food and Environment Atlas .csv Files` to download the data. Create a new directory `data/` in the home directory of the repo, then extract all of the files into it.

## Running Code

We will be using Docker for this project to run our code in a container. This allows your code to run in an isolated environment, without having to worry about your personal computer machine's environment. Read more about how docker works [here](https://www.docker.com/).

The configuration of our docker container is defined in `docker-compose.yml`. This configuration is the bare minimum to work on the project, but these configurations can scale greatly, such as starting up multiple containers that communicate with each other. The fields in this docker-compose file are image, volumes, and ports.

- `build`: The path to the Dockerfile that the container is being built from. This pulls from [Docker Hub](https://hub.docker.com/), and the container's dependencies and installed packages are defined here. Additional configuration is defined in the Dockerfile
- `volumes`: This allows us to interact with our container's file system. Its elements are formatted as follows: `<local_path_relative_to_docker-compose.yml>:<absolute_path_in_docker_container>`
- `ports`: Mapping ports within our container to ports in our local machine. Its elements are formatted as follows: `<port_on_local>:<port_in_container>`

For more information on Docker Compose, see [the Docker documentation](https://docs.docker.com/compose/compose-file/compose-file-v3/).

```yml
build: .
volumes:
    - ./src:/home/jovyan/src
    - ./data:/home/jovyan/data
ports:
    - 8888:8888
```

To run the Spark environment in Jupyter Notebook, run `docker compose up` in your terminal, in the same directory as `docker-compose.yml`

Look for this text in the terminal output:

```plaintext
To access the server, open this file in a browser:
file:///home/jovyan/.local/share/jupyter/runtime/jpserver-7-open.html
 Or copy and paste one of these URLs:
         http://476665dae8a3:8888/lab?token=<a_generated_token>
         http://127.0.0.1:8888/lab?token=<a_generated_token>
```

Open the second url (the one that starts with `127.0.0.1`) to open Jupyter Lab. This is where you will be doing your work.

You will be using Jupyter Notebook to run your Python/PySpark code. See the [Jupyter docs](https://docs.jupyter.org/en/latest/) for more info.

## Project Info

In this project you will be looking at food environment data, such as proximity to a grocery store, restaurant, etc. over different areas in the United States. Read the [documentation](https://www.ers.usda.gov/data-products/food-environment-atlas/documentation/) to help understand the data before continuing.

### Getting Started

Open `src/tutorial.ipynb` in Jupyter Lab and experiment with the PySpark tools, as well as the data. You will learn some basics of how to process data, and visualize it.

### Task 1

When working with real-world data, you will encounter dirty data. This could be either due to the nature of the data, or simple human error. For this task, use PySpark to get the total population in each state by summing the populations from its counties (from `SupplementalDataCounty.csv`). State population already exists, but use the county data as an exercise. This data has some issues, so you will have to do some cleaning to properly visualize it.

In your report, write what issues you came across, and how you solved them. Include a screenshot of the resulting choropleth map that you created from this data.

### Task 2

After reading through the `variable list` file, compare the average number of grocery stores available per 1000 people  in each state, in 2011 and 2016. Vizualize this comparison in a double bar graph `for the states with the largest change`.

In your report, include a screenshot of the bar graph.
