# UCR-CS236-Project

- [UCR-CS236-Project](#ucr-cs236-project)
  - [Setup](#setup)
    - [Docker Setup](#docker-setup)
    - [Project Setup](#project-setup)
    - [Getting Data](#getting-data)
  - [Running Code](#running-code)
    - [Spark](#spark)
    - [Hadoop](#hadoop)
      - [Note for Mac users with Apple Silicon (M1 and M2)](#note-for-mac-users-with-apple-silicon-m1-and-m2)
      - [Hadoop (Continued)](#hadoop-continued)
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

Clone this repository and cd into it. You will see `<framework>/data/`, which will be where you put your data files. You will also see `spark/src/` and `hadoop/CS236_project`, where your notebooks will be.

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

### Spark

To run the Spark environment in Jupyter Notebook, run `docker compose build`, then `docker compose up` in your terminal, in the same directory as `docker-compose.yml`. For future runs, you only need to run `docker compose up`.

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

### Hadoop

Running Hadoop code is a little more complicated. First, download the [Hadoop binaries](https://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-3.3.6/hadoop-3.3.6.tar.gz) and put the file in the `hadoop/` directory. Then, run `docker compose build`. This will take about 10 minutes.

To start the Docker container, uuuuu the command `docker compose up -d`. Note that we add `-d`. This is because we want to run the container in detached mode so we can use the same terminal while the container runs. To run the code, we will run bash inside the container.

Run the command `docker compose exec hadoop bash` to run bash int he container. Then, go to `/home/CS236_project`.

#### Note for Mac users with Apple Silicon (M1 and M2)

Whenever you bash into the container, you need to reset `JAVA_HOME`. To do this, run the following two commands:

```bash
unset JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-arm64
```

#### Hadoop (Continued)

Whenever your code is ready to recompile, run the command `mvn package`. This will compile your Java code into a jar.

To run the jar in Hadoop, run the following command:

```bash
hadoop jar target/CS236_project-1.0-SNAPSHOT.jar <path_to_input_file> <path_to_output_directory>
```

Note that the output directory must not exist before running Hadoop.

To see how long the Hadoop code took to run, refer to `hadoop/execution_time.txt`.

Look through `hadoop/CS236_project/src/main/java/edu/ucr/cs/cs236/App.java` to understand the structure of the code, and complete the TODO sections to write your results. Your results should be in `CS236_project/<your_output_directory_name>/part-r-00000`. To do the visualization, put the output file in the `data` directory of your Spark environment and use Python & Pandas to visualize it the same way you did for Spark.

## Project Info

In this project you will be looking at food environment data, such as proximity to a grocery store, restaurant, etc. over different areas in the United States. Read the [documentation](https://www.ers.usda.gov/data-products/food-environment-atlas/documentation/) to help understand the data before continuing. You will be performing these tasks in both Hadoop, then Spark.

It is recommended but not required to do the task in Spark first since the syntax is simpler.

### Getting Started

Open `src/tutorial.ipynb` in Jupyter Lab and experiment with the PySpark tools, as well as the data. You will learn some basics of how to process data, and visualize it.

### Task 1

When working with real-world data, you will encounter dirty data. This could be either due to the nature of the data, or simple human error. For this task, get the total population in each state in `2010` by summing the populations from its counties (from `SupplementalDataCounty.csv`). State population data already exists, but use the county data as an exercise. This data has some issues, so you will have to do some cleaning to properly visualize it.

In your report, write what issues you came across, and how you solved them. Include a screenshot of the resulting choropleth map that you created from this data. Also include the runtimes of the computation for both Spark and Hadoop (use `%%timeit` and all in 1 cell for Spark). This includes file reading, the computation, and file writing.

### Task 2

After reading through the `variable list` file, compare the average number of grocery stores available per 1000 people in each state, in 2011 and 2016. Vizualize this before-and-after in a double bar graph ([see method 1](https://www.geeksforgeeks.org/plot-multiple-columns-of-pandas-dataframe-on-bar-chart-with-matplotlib/)) `for the 3 states with the largest change between the years`.

In your report, include a screenshot of the bar graph.
